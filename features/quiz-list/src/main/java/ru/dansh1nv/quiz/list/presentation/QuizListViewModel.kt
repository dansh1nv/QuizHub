package ru.dansh1nv.quiz.list.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.dansh1nv.common.addOrAppend
import ru.dansh1nv.core.presentation.viewModel.BaseMviViewModel
import ru.dansh1nv.core.presentation.ScreenState
import ru.dansh1nv.core.presentation.model.UIStatus
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar.IconModel
import ru.dansh1nv.designsystem.theme.utils.`typealias`.UIDrawable
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.mappers.EventPieChartMapper
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.ShakerQuizMapper
import ru.dansh1nv.quiz.list.mappers.ActionEventsMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.models.bottomsheet.BottomSheetModels
import ru.dansh1nv.quiz.list.models.filters.Filters
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz.list.models.sorting.Sort
import ru.dansh1nv.quiz_list_domain.interactors.CityInteractor
import ru.dansh1nv.quiz_list_domain.interactors.QuizListInteractor
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.core.presentation.model.ActionEvents
import ru.dansh1nv.core.presentation.ActionEventsListener
import timber.log.Timber

internal class QuizListViewModel(
    private val interactor: QuizListInteractor,
    private val cityInteractor: CityInteractor,
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
    private val shakerQuizMapper: ShakerQuizMapper,
    private val actionEventsMapper: ActionEventsMapper,
    private val resourceManager: IResourceManager,
    private val bottomSheetController: BottomSheetController,
    private val actionEventsListener: ActionEventsListener
) : BaseMviViewModel<QuizListState, QuizListSideEffect, QuizListEvent>(
    initialState = QuizListState()
), BottomSheetController by bottomSheetController {

    private val quizMap = mutableMapOf<Organization, MutableList<QuizUI>>()

    override suspend fun onLaunch() {
        fetchQuizList()
    }

    override fun handleEvent(event: QuizListEvent) {
        when (event) {
            is ScreenEvent -> onScreenEvent(event)
            is BottomSheetEvent -> onBottomSheetEvent(event)
        }
    }

    private fun fetchQuizList() = viewModelScope.launch {
        interactor.getAllQuizList(17)
            .map { quizList ->
                quizList.map { quiz ->
                    when (quiz) {
                        is QuizPlease -> quizMap.addOrAppend(
                            key = Organization.QUIZ_PLEASE,
                            value = quizPleaseMapper.mapToQuizUI(quiz)
                        )

                        is SQuiz -> quizMap.addOrAppend(
                            key = Organization.SQUIZ,
                            value = squizMapper.mapToQuizUI(quiz)
                        )

                        is ShakerQuiz -> quizMap.addOrAppend(
                            key = Organization.SHAKER_QUIZ,
                            value = shakerQuizMapper.mapToQuizUI(quiz)
                        )
                    }
                }
            }
            .map {
                quizMap.values
                    .flatten()
                    .sortedBy { it.formattedDate?.date }
            }
            .catch { ex ->
                Timber.e(ex)
                updateState { copy(uiStatus = UIStatus.Error) }
            }
            .flowOn(Dispatchers.Default)
            .onEach { quizList ->
                updateState {
                    copy(
                        quizList = quizList,
                        uiStatus = if (quizMap.values.isNotEmpty()) {
                            UIStatus.Loaded
                        } else {
                            UIStatus.Loading
                        }
                    )
                }
            }
            .flowOn(Dispatchers.Main)
            .collect()
    }

    private fun updateCurrentTab(index: Int) = updateState { copy(selectedTabIndex = index) }

    private fun onScreenEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSortButtonClick -> showSorting()
            is ScreenEvent.OnLocationClick -> {}
            is ScreenEvent.OnFiltersButtonClick -> showFilters()
            is ScreenEvent.OnTabClick -> updateCurrentTab(event.index)
            is ScreenEvent.OnRefresh -> fetchQuizList()
            is ScreenEvent.OnCalendarClick -> handleCalendarClick()
            is ScreenEvent.OnCardItemClicked -> {
                //Добавить экран детализации квиза
                //navigateToQuizDetails(event.id)
            }
            is ScreenEvent.OnShareEventClick -> handleShareEventClick(event.id)
        }
    }

    private fun handleShareEventClick(id: String) {
        val quiz = container.stateFlow.value.quizList.firstOrNull { it.id == id } ?: return
        val shareText = actionEventsMapper.mapToShareText(quiz)
        actionEventsListener.onActionEvent(
            ActionEvents.ShareEvent(shareText)
        )
    }

    private fun handleCalendarClick() {
        val quizList = container.stateFlow.value.quizList
        val calendarEvents = EventPieChartMapper.mapToCalendarEventsUI(quizList)

        bottomSheetController.show(
            BottomSheetModels.CalendarBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.calendar_title),
                    trailIcon = IconModel(UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
                ),
                events = calendarEvents
            )
        )
    }

    private fun onBottomSheetEvent(event: BottomSheetEvent) {
        when (event) {
            is BottomSheetEvent.OnFilterClick -> applyFilters(event.organization)
            is BottomSheetEvent.OnSortClick -> applySorting(event.sort)
        }
    }

    private fun applySorting(sort: Sort) {
        updateState { copy(sort = sort) }
        showQuizList()
    }

    private fun showFilters() {
        bottomSheetController.show(
            BottomSheetModels.FilterBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.filter_title),
                    trailIcon = IconModel(
                        iconRes = UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
                )
            )
        )
    }

    private fun showSorting() {
        bottomSheetController.show(
            BottomSheetModels.SortingBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.sorting_title),
                    trailIcon = IconModel(
                        iconRes = UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
                )
            )
        )
    }

    private fun applyFilters(organization: Organization?) {
        updateState {
            copy(filters = Filters.entries.firstOrNull { filter ->
                filter.organization == organization
            })
        }
        showQuizList()
    }

    private fun showQuizList() = updateState {
        if (uiStatus != UIStatus.Loaded) return@updateState this

        val quizList = quizMap.getOrDefault(
            key = this.filters?.organization,
            defaultValue = quizMap.values.flatten()
        )

        val sortedList = when (this.sort) {
            Sort.ASC_DATE -> quizList.sortedBy { it.formattedDate?.date }
            Sort.DESC_DATE -> quizList.sortedByDescending { it.formattedDate?.date }
        }
        copy(quizList = sortedList)
    }

    private fun navigateToQuizDetails(quizId: String) {
        postSideEffect(QuizListSideEffect.NavigateQuizDetails(quizId))
    }
}

@Immutable
internal data class QuizListState(
    val uiStatus: UIStatus = UIStatus.Loading,
    val selectedTabIndex: Int = 0,
    val quizList: List<QuizUI> = emptyList(),
    val featureToggle: FeatureToggle = FeatureToggle(),
    val filters: Filters? = null,
    val sort: Sort = Sort.ASC_DATE,
) : ScreenState

internal data class FeatureToggle(
    val isFavouriteFeatureEnabled: Boolean = false,
    val isFiltersFeatureEnabled: Boolean = true,
    val isSortFeatureEnabled: Boolean = true,
    val isCalendarFeatureEnable: Boolean = true
)
package ru.dansh1nv.quiz.list.presentation

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dansh1nv.core.presentation.BaseMviScreenModel
import ru.dansh1nv.core.presentation.model.UIStatus
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar
import ru.dansh1nv.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar.IconModel
import ru.dansh1nv.designsystem.theme.utils.`typealias`.UIDrawable
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.ShakerQuizMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.models.filters.Filters
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz.list.models.sorting.Sort
import ru.dansh1nv.quiz.list.presentation.composable.bottomsheet.BottomSheetModels
import ru.dansh1nv.quiz_list_domain.interactors.QuizListInteractor
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import timber.log.Timber

internal class QuizListViewModel(
    private val interactor: QuizListInteractor,
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
    private val shakerQuizMapper: ShakerQuizMapper,
    private val resourceManager: IResourceManager,
    private val bottomSheetController: BottomSheetController,
) : BaseMviScreenModel<QuizListState, QuizListSideEffect, QuizListEvent>(
    initialState = QuizListState()
), BottomSheetController by bottomSheetController {

    private val quizMap = mutableMapOf<Organization, MutableList<QuizUI>>()

    init {
        Organization.entries.forEach { organization ->
            quizMap[organization] = mutableListOf()
        }
        fetchQuizList()
    }

    override fun handleEvent(event: QuizListEvent) {
        when (event) {
            is ScreenEvent -> onScreenEvent(event)
            is BottomSheetEvent -> onBottomSheetEvent(event)
        }
    }

    private fun fetchQuizList() = screenModelScope.launch {
        interactor.getAllQuizList(17)
            .map { quizList ->
                quizList.mapNotNull { quiz ->
                    when (quiz) {
                        is QuizPlease -> quizMap[Organization.QUIZ_PLEASE]?.add(
                            quizPleaseMapper.mapToQuizUI(quiz)
                        )

                        is SQuiz -> quizMap[Organization.SQUIZ]?.add(
                            squizMapper.mapToQuizUI(quiz)
                        )

                        is ShakerQuiz -> quizMap[Organization.SHAKER_QUIZ]?.add(
                            shakerQuizMapper.mapToQuizUI(quiz)
                        )

                        else -> null
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
                updateState { state ->
                    state.copy(uiStatus = UIStatus.Error)
                }
            }
            .flowOn(Dispatchers.Default)
            .onEach { quizList ->
                withContext(Dispatchers.Main) {
                    updateState { state ->
                        state.copy(
                            quizList = quizList,
                            uiStatus = if (quizMap.values.isNotEmpty()) {
                                UIStatus.Loaded
                            } else {
                                UIStatus.Loading
                            }
                        )
                    }
                }
            }.collect()
    }

    private fun updateCurrentTab(index: Int) = updateState { state ->
        state.copy(selectedTabIndex = index)
    }

    private fun onScreenEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSortButtonClick -> showSorting(event.isShow)
            is ScreenEvent.OnLocationClick -> {}
            is ScreenEvent.OnFiltersButtonClick -> showFilters(event.isShow)
            is ScreenEvent.OnTabClick -> updateCurrentTab(event.index)
            is ScreenEvent.OnRefresh -> fetchQuizList()
            is ScreenEvent.BottomSheetDismiss -> {}
        }
    }

    private fun onBottomSheetEvent(event: BottomSheetEvent) {
        when (event) {
            is BottomSheetEvent.OnFilterClick -> applyFilters(event.organization)
            is BottomSheetEvent.OnSortClick -> applySorting(event.sort)
        }
    }

    private fun applySorting(sort: Sort) {
        updateState { state ->
            state.copy(
                sort = sort
            )
        }
        showQuizList()
    }

    private fun showFilters(isShow: Boolean) {
        bottomSheetController.show(
            BottomSheetModels.FilterBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.filter_title),
                    trailIcon = IconModel(
                        UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
                )
            )
        )
//        updateState { state ->
//            state.copy(isFiltersShow = isShow)
//        }
    }

    private fun showSorting(isShow: Boolean) = updateState { state ->
        state.copy(isSortingShow = isShow)
    }

    private fun applyFilters(organization: Organization?) {
        updateState { state ->
            state.copy(
                filters = Filters.entries.firstOrNull { filter ->
                    filter.organization == organization
                }
            )
        }
        showQuizList()
    }

    private fun showQuizList() = updateState { state ->
        if (state.uiStatus != UIStatus.Loaded) return@updateState state

        val quizList = quizMap.getOrDefault(
            key = state.filters?.organization,
            defaultValue = quizMap.values.flatten()
        )

        val sortedList = when (state.sort) {
            Sort.ASC_DATE -> quizList.sortedBy { it.formattedDate?.date }
            Sort.DESC_DATE -> quizList.sortedByDescending { it.formattedDate?.date }
        }
        state.copy(
            quizList = sortedList
        )
    }
}

internal data class QuizListState(
    val uiStatus: UIStatus = UIStatus.Loading,
    val selectedTabIndex: Int = 0,
    val quizList: List<QuizUI> = emptyList(),
    val featureToggle: FeatureToggle = FeatureToggle(),
    val filters: Filters? = null,
    val sort: Sort = Sort.ASC_DATE,
    val isFiltersShow: Boolean = false,
    val isSortingShow: Boolean = false,
)

internal data class FeatureToggle(
    val isFavouriteFeatureEnabled: Boolean = false,
    val isFiltersFeatureEnabled: Boolean = true,
    val isSortFeatureEnabled: Boolean = true,
)
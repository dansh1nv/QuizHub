package ru.dansh1nv.quiz.list.presentation

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dansh1nv.core.presentation.BaseScreenModel
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.ShakerQuizMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.models.bottomsheet.BottomSheet
import ru.dansh1nv.quiz.list.models.filters.Filters
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz.list.models.sorting.Sort
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
) : BaseScreenModel<QuizListEvent>() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val quizMap = mutableMapOf<Organization, MutableList<QuizUI>>()

    init {
        Organization.entries.forEach { organization ->
            quizMap[organization] = mutableListOf()
        }
        fetchQuizList()
    }

    private fun fetchQuizList() = screenModelScope.launch(Dispatchers.Default) {
        val city = if(state.value is State.Loaded) {
            (state.value as State.Loaded).city ?: CityModel("Санкт-Петербург", 17L)
        } else {
            CityModel("Санкт-Петербург", 17L)
        }
        _state.update { State.Loading }
        interactor.getAllQuizList(city.id)
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
                _state.update { State.Error }
            }
            .flowOn(Dispatchers.Default)
            .onEach { quizList ->
                withContext(Dispatchers.Main) {
                    _state.update {
                        State.Loaded(
                            quizList = quizList
                        )
                    }
                }
            }
            .collect()
    }

    private fun updateCurrentTab(index: Int) {
        if (state.value is State.Loaded) {
            _state.update { state ->
                (state as State.Loaded).copy(selectedTabIndex = index)
            }
        }
    }

    override fun onUIEvent(event: QuizListEvent) {
        when (event) {
            is ScreenEvent -> onScreenEvent(event)
            is BottomSheetEvent -> onBottomSheetEvent(event)
        }
    }

    private fun onScreenEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSortButtonClick -> showSorting()
            is ScreenEvent.OnLocationClick -> showLocation()
            is ScreenEvent.OnFiltersButtonClick -> showFilters()
            is ScreenEvent.OnTabClick -> updateCurrentTab(event.index)
            is ScreenEvent.OnRefresh -> fetchQuizList()
            is ScreenEvent.BottomSheetDismiss -> {
                _state.update { screenState ->
                    (screenState as State.Loaded).copy(bottomSheet = null)
                }
            }
        }
    }

    private fun onBottomSheetEvent(event: BottomSheetEvent) {
        when (event) {
            is BottomSheetEvent.OnFilterClick -> applyFilters(event.organization)
            is BottomSheetEvent.OnSortClick -> applySorting(event.sort)
            is BottomSheetEvent.OnLocationClick -> setupCity(event.city)
        }
    }

    private fun applySorting(sort: Sort) {
        _state.update { screenState -> (screenState as State.Loaded).copy(sort = sort) }
        showQuizList()
    }

    private fun setupCity(city: CityModel) {
        _state.update { screenState ->
            if (screenState !is State.Loaded) return
            screenState.copy(city = city)
        }
        fetchQuizList()
    }

    private fun showFilters() {
        _state.update { screenState ->
            if (screenState !is State.Loaded) return
            screenState.copy(bottomSheet = BottomSheet.FiltersBottomSheet)
        }
    }

    private fun showSorting() {
        _state.update { screenState ->
            if (screenState !is State.Loaded) return
            screenState.copy(bottomSheet = BottomSheet.SortingBottomSheet)
        }
    }

    private fun showLocation() {
        _state.update { screenState ->
            if (screenState !is State.Loaded) return
            screenState.copy(bottomSheet = BottomSheet.LocationBottomSheet)
        }
    }

    private fun applyFilters(organization: Organization?) {
        _state.update { screenState ->
            if (screenState !is State.Loaded) return
            screenState.copy(
                filters = Filters.entries.firstOrNull { filter ->
                    filter.organization == organization
                }
            )
        }
        showQuizList()
    }

    private fun showQuizList() {
        if (state.value !is State.Loaded) return

        val filters = (state.value as State.Loaded).filters
        val sort = (state.value as State.Loaded).sort

        val quizList = quizMap.getOrDefault(
            key = filters?.organization,
            defaultValue = quizMap.values.flatten()
        )

        val sortedList = when (sort) {
            Sort.ASC_DATE -> quizList.sortedBy { it.formattedDate?.date }
            Sort.DESC_DATE -> quizList.sortedByDescending { it.formattedDate?.date }
        }
        _state.update {
            State.Loaded(
                quizList = sortedList
            )
        }
    }

}

internal sealed class State {
    data object Loading : State()
    data object Error : State()
    data class Loaded(
        val selectedTabIndex: Int = 0,
        val quizList: List<QuizUI> = emptyList(),
        val featureToggle: FeatureToggle = FeatureToggle(),
        val filters: Filters? = null,
        val sort: Sort = Sort.ASC_DATE,
        val city: CityModel? = null,
        val bottomSheet: BottomSheet? = null,
    ) : State()
}

internal data class FeatureToggle(
    val isFavouriteFeatureEnabled: Boolean = false,
)
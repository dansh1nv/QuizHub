package ru.dansh1nv.quiz.list.presentation

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dansh1nv.core.presentation.BaseScreenModel
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.models.Organization
import ru.dansh1nv.quiz.list.models.QuizUI
import ru.dansh1nv.quiz_list_domain.interactors.QuizListInteractor
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.SQuiz

internal class QuizListViewModel(
    private val interactor: QuizListInteractor,
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
//    private val bottomSheetController: BottomSheetController,
) : BaseScreenModel<ScreenEvent>()/*, BottomSheetController by bottomSheetController*/ {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val quizMap = mutableMapOf<Organization, MutableList<QuizUI>>()

    init {
        screenModelScope.launch {
            Organization.entries.forEach { organization ->
                quizMap[organization] = mutableListOf()
            }
            interactor.getAllQuizList()
                .map { quizList ->
                    quizList.mapNotNull { quiz ->
                        when (quiz) {
                            is QuizPlease -> quizMap[Organization.QUIZ_PLEASE]?.add(
                                quizPleaseMapper.mapToQuizPlease(quiz)
                            )

                            is SQuiz -> quizMap[Organization.SQUIZ]?.add(
                                squizMapper.mapToQuizUI(quiz)
                            )

                            else -> null
                        }
                    }
                }
                .flowOn(Dispatchers.Default)
                .onEach {
                    val quizList = quizMap.values
                        .flatten()
                        .sortedBy { quiz -> quiz.formattedDate.date }
                    _state.update {
                        State.Loaded(
                            quizList = quizList
                        )
                    }
                }
                .collect()
        }
    }

    private fun updateCurrentTab(index: Int) {
        if (state.value is State.Loaded) {
            _state.update { state ->
                (state as State.Loaded).copy(selectedTabIndex = index)
            }
        }
    }

    override fun onUIEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSortClick -> {}
            is ScreenEvent.OnLocationClick -> {}
            is ScreenEvent.OnFiltersClick -> showFilters()
            is ScreenEvent.OnTabClick -> updateCurrentTab(event.index)
        }
    }

    private fun showFilters() {

    }

}

sealed class State {
    data object Loading : State()
    data object Error : State()
    data class Loaded(
        val selectedTabIndex: Int = 0,
        val quizList: List<QuizUI> = emptyList(),
    ) : State()
}
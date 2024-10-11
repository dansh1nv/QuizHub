package ru.dansh1nv.quiz.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.models.QuizUI
import ru.dansh1nv.quiz_list_domain.interactors.QuizListInteractor
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.SQuiz

internal class QuizListViewModel(
    private val interactor: QuizListInteractor,
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val quizzes = mutableListOf<QuizUI>()

    init {
        viewModelScope.launch {
            interactor.getAllQuizList()
                .map { quizList ->
                    quizList.mapNotNull { quiz ->
                        when (quiz) {
                            is QuizPlease -> quizPleaseMapper.mapToQuizPlease(quiz)
                            is SQuiz -> squizMapper.mapToQuizUI(quiz)
                            else -> null
                        }
                    }
                }
                .flowOn(Dispatchers.Default)
//                .onEach {
//                    quizzes.sortByDescending { quiz -> quiz.gameDate }
//                }
                .collect { quizList ->
                    quizzes.addAll(quizList)
                    _state.update {
                        State.Success(
                            quizList = quizzes.toList()
                        )
                    }
                }
        }
    }

    fun onUIEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OnSortClick -> {}
            is UIEvent.OnLocationClick -> {}
            is UIEvent.OnFiltersClick -> {}
            is UIEvent.OnTabClick -> {}
        }
    }

}

sealed class State {
    data object Loading : State()
    data object Error : State()
    class Success(val quizList: List<QuizUI>) : State()
}
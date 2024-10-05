package ru.dansh1nv.quiz.list.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel

val quizListModule = module {
    factory { SquizMapper }
    factory { QuizPleaseMapper }
    viewModelOf(::QuizListViewModel)
}
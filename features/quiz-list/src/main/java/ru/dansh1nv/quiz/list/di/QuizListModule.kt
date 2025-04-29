package ru.dansh1nv.quiz.list.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dansh1nv.quiz.list.mappers.CommonMapper
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.ShakerQuizMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel

fun quizListModule() = module {
    factoryOf(::SquizMapper)
    factoryOf(::QuizPleaseMapper)
    factoryOf(::ShakerQuizMapper)
    factoryOf(::CommonMapper)
    factoryOf(::QuizListViewModel)
}
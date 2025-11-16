package ru.quizHub.quizList.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.quizHub.quizList.mappers.ActionEventsMapper
import ru.quizHub.quizList.mappers.CommonMapper
import ru.quizHub.quizList.mappers.QuizPleaseMapper
import ru.quizHub.quizList.mappers.RudaGamesMapper
import ru.quizHub.quizList.mappers.ShakerQuizMapper
import ru.quizHub.quizList.mappers.SquizMapper
import ru.quizHub.quizList.presentation.QuizListViewModel

fun quizListModule() = module {
    factoryOf(::SquizMapper)
    factoryOf(::QuizPleaseMapper)
    factoryOf(::ShakerQuizMapper)
    factoryOf(::RudaGamesMapper)
    factoryOf(::CommonMapper)
    factoryOf(::ActionEventsMapper)
    factoryOf(::QuizListViewModel)
}
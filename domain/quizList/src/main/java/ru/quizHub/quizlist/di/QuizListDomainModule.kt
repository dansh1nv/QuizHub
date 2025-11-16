package ru.quizHub.quizlist.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.quizHub.quizlist.interactors.CommonInteractor
import ru.quizHub.quizlist.interactors.GeoInfoInteractor
import ru.quizHub.quizlist.interactors.QuizListInteractor
import ru.quizHub.quizlist.interactors.QuizPleaseInteractor
import ru.quizHub.quizlist.interactors.RudaGamesInteractor
import ru.quizHub.quizlist.interactors.ShakerQuizInteractor
import ru.quizHub.quizlist.interactors.SquizInteractor

fun quizListDomainModule() = module {

    factoryOf(::SquizInteractor)
    factoryOf(::QuizPleaseInteractor)
    factoryOf(::QuizListInteractor)
    factoryOf(::ShakerQuizInteractor)
    factoryOf(::GeoInfoInteractor)
    factoryOf(::CommonInteractor)
    factoryOf(::RudaGamesInteractor)
}

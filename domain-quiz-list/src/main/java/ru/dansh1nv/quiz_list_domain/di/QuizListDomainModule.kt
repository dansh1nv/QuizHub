package ru.dansh1nv.quiz_list_domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.dansh1nv.quiz_list_domain.interactors.QuizListInteractor
import ru.dansh1nv.quiz_list_domain.interactors.QuizPleaseInteractor
import ru.dansh1nv.quiz_list_domain.interactors.ShakerQuizInteractor
import ru.dansh1nv.quiz_list_domain.interactors.SquizInteractor
import ru.dansh1nv.quiz_list_domain.models.common.ActionEventsListener

fun quizListDomainModule() = module {

    factoryOf(::SquizInteractor)
    factoryOf(::QuizPleaseInteractor)
    factoryOf(::QuizListInteractor)
    factoryOf(::ShakerQuizInteractor)
    singleOf(::ActionEventsListener)
}

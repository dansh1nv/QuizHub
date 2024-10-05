package ru.dansh1nv.quiz_list_domain.di

import org.koin.dsl.module
import ru.dansh1nv.quiz_list_domain.interactors.QuizListInteractor
import ru.dansh1nv.quiz_list_domain.interactors.QuizPleaseInteractor
import ru.dansh1nv.quiz_list_domain.interactors.SquizInteractor

val quizListDomainModule = module {

    factory<SquizInteractor> {
        SquizInteractor(
            repository = get(),
        )
    }
    factory<QuizPleaseInteractor> {
        QuizPleaseInteractor(
            repository = get(),
        )
    }

    factory<QuizListInteractor> {
        QuizListInteractor(
            squizInteractor = get(),
            quizPleaseInteractor = get(),
        )
    }
}

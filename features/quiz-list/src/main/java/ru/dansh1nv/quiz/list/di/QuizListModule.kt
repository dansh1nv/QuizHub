package ru.dansh1nv.quiz.list.di

import org.koin.dsl.module
import ru.dansh1nv.quiz.list.mappers.CommonMapper
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.ShakerQuizMapper
import ru.dansh1nv.quiz.list.mappers.SquizMapper
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel

fun quizListModule() = module {
    factory {
        SquizMapper(
            resourceManager = get(),
            commonMapper = get(),
        )
    }

    factory {
        QuizPleaseMapper(
            resourceManager = get(),
            commonMapper = get(),
        )
    }

    factory {
        ShakerQuizMapper(
            commonMapper = get(),
        )
    }

    factory {
        CommonMapper(
            resourceManager = get(),
        )
    }

    factory {
        QuizListViewModel(
            interactor = get(),
            squizMapper = get(),
            quizPleaseMapper = get(),
            shakerQuizMapper = get(),
        )
    }
}
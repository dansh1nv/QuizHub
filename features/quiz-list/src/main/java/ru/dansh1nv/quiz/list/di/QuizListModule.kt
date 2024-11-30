package ru.dansh1nv.quiz.list.di

import org.koin.dsl.module
import ru.dansh1nv.quiz.list.mappers.CommonMapper
import ru.dansh1nv.quiz.list.mappers.QuizMapper
import ru.dansh1nv.quiz.list.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.list.mappers.RudaGamesMapper
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
        RudaGamesMapper(
            resourceManager = get(),
            commonMapper = get(),
        )
    }

    factory {
        QuizMapper(
            squizMapper = get(),
            quizPleaseMapper = get(),
            shakerQuizMapper = get(),
            rudaGamesMapper = get(),
        )
    }

    factory {
        QuizListViewModel(
            interactor = get(),
            quizMapper = get(),
        )
    }
}
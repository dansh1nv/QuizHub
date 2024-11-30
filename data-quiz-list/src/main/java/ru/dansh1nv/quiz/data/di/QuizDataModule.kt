package ru.dansh1nv.quiz.data.di

import org.koin.dsl.module
import ru.dansh1nv.quiz.data.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.dansh1nv.quiz.data.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.dansh1nv.quiz.data.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.dansh1nv.quiz.data.datasource.squiz.SquizLocalDataSource
import ru.dansh1nv.quiz.data.datasource.squiz.SquizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.QuizPleaseDataMapper
import ru.dansh1nv.quiz.data.mappers.ShakerQuizDataMapper
import ru.dansh1nv.quiz.data.mappers.SquizDataMapper
import ru.dansh1nv.quiz.data.repositories.QuizPleaseRepository
import ru.dansh1nv.quiz.data.repositories.RudaGamesRepository
import ru.dansh1nv.quiz.data.repositories.ShakerQuizRepository
import ru.dansh1nv.quiz.data.repositories.SquizRepository
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository
import ru.dansh1nv.quiz_list_domain.repository.IRudaGamesRepository
import ru.dansh1nv.quiz_list_domain.repository.ISQuizRepository
import ru.dansh1nv.quiz_list_domain.repository.IShakerQuizRepository

fun quizDataModule() = module {

    factory<SquizDataMapper> { SquizDataMapper() }

    factory<SquizLocalDataSource> {
        SquizLocalDataSource(database = get())
    }

    factory<SquizRemoteDataSource> {
        SquizRemoteDataSource(api = get())
    }

    single<ISQuizRepository> {
        SquizRepository(
//            localDataSource = get(),
            remoteDataSource = get(),
            quizMapper = get(),
        )
    }

    factory<QuizPleaseDataMapper> { QuizPleaseDataMapper() }

    factory<QuizPleaseRemoteDataSource> { QuizPleaseRemoteDataSource(api = get()) }

    single<IQuizPleaseRepository> {
        QuizPleaseRepository(
            remoteDataSource = get(),
            mapper = get(),
        )
    }

    factory<ShakerQuizDataMapper> { ShakerQuizDataMapper() }
    factory<ShakerQuizRemoteDataSource> { ShakerQuizRemoteDataSource(api = get()) }
    single<IShakerQuizRepository> {
        ShakerQuizRepository(
            remoteDataSource = get(),
            mapper = get()
        )
    }
    factory<RudaGamesRemoteDataSource> { RudaGamesRemoteDataSource(api = get()) }
    single<IRudaGamesRepository> {
        RudaGamesRepository(
            remoteDataSource = get(),
            mapper = get(),
        )
    }

}
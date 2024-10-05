package ru.dansh1nv.quiz.data.di

import org.koin.dsl.module
import ru.dansh1nv.quiz.data.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.dansh1nv.quiz.data.repositories.SquizRepository
import ru.dansh1nv.quiz.data.datasource.squiz.LocalDataSource
import ru.dansh1nv.quiz.data.datasource.squiz.SquizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz.data.mappers.SquizMapper
import ru.dansh1nv.quiz.data.repositories.QuizPleaseRepository
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository
import ru.dansh1nv.quiz_list_domain.repository.ISQuizRepository

val quizDataModule = module {

    factory<SquizMapper> { SquizMapper }

    factory<LocalDataSource> {
        LocalDataSource(database = get())
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

    factory<QuizPleaseMapper> { QuizPleaseMapper }

    factory<QuizPleaseRemoteDataSource> { QuizPleaseRemoteDataSource(api = get()) }

    single<IQuizPleaseRepository> {
        QuizPleaseRepository(
            remoteDataSource = get(),
            mapper = get(),
        )
    }

}
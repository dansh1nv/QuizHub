package ru.dansh1nv.quiz.data.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.dansh1nv.quiz.data.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.dansh1nv.quiz.data.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.dansh1nv.quiz.data.datasource.squiz.LocalDataSource
import ru.dansh1nv.quiz.data.datasource.squiz.SquizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.QuizPleaseDataMapper
import ru.dansh1nv.quiz.data.mappers.ShakerQuizDataMapper
import ru.dansh1nv.quiz.data.mappers.SquizDataMapper
import ru.dansh1nv.quiz.data.repositories.QuizPleaseRepository
import ru.dansh1nv.quiz.data.repositories.ShakerQuizRepository
import ru.dansh1nv.quiz.data.repositories.SquizRepository
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository
import ru.dansh1nv.quiz_list_domain.repository.ISQuizRepository
import ru.dansh1nv.quiz_list_domain.repository.IShakerQuizRepository

fun quizDataModule() = module {

    factoryOf(::SquizDataMapper)
    factoryOf(::LocalDataSource)
    factoryOf(::SquizRemoteDataSource)
    single<ISQuizRepository> {
        SquizRepository(
//            localDataSource = get(),
            remoteDataSource = get(),
            quizMapper = get(),
        )
    }

    factoryOf(::QuizPleaseDataMapper)
    factoryOf(::QuizPleaseRemoteDataSource)
    single<IQuizPleaseRepository> {
        QuizPleaseRepository(
            remoteDataSource = get(),
            mapper = get(),
        )
    }

    factoryOf(::ShakerQuizDataMapper)
    factoryOf(::ShakerQuizRemoteDataSource)
    single<IShakerQuizRepository> {
        ShakerQuizRepository(
            remoteDataSource = get(),
            mapper = get()
        )
    }
}
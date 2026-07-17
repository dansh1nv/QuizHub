package ru.quizHub.quizList.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.quizHub.quizList.datasource.QuizListLocalDataSource
import ru.quizHub.quizList.datasource.common.GeoInfoRemoteDataSource
import ru.quizHub.quizList.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.quizHub.quizList.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.quizHub.quizList.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.quizHub.quizList.datasource.smuzi.SmuziRemoteDataSource
import ru.quizHub.quizList.datasource.squiz.LocalDataSource
import ru.quizHub.quizList.datasource.squiz.SquizRemoteDataSource
import ru.quizHub.quizList.datasource.wowQuiz.WowQuizRemoteDataSource
import ru.quizHub.quizList.mappers.CommonDataMapper
import ru.quizHub.quizList.mappers.QuizDBOMapper
import ru.quizHub.quizList.mappers.QuizPleaseDataMapper
import ru.quizHub.quizList.mappers.RudaGamesDataMapper
import ru.quizHub.quizList.mappers.ShakerQuizDataMapper
import ru.quizHub.quizList.mappers.SmuziDataMapper
import ru.quizHub.quizList.mappers.SquizDataMapper
import ru.quizHub.quizList.mappers.WowQuizDataMapper
import ru.quizHub.quizList.repositories.CommonRepository
import ru.quizHub.quizList.repositories.QuizListRepository
import ru.quizHub.quizList.repositories.QuizPleaseRepository
import ru.quizHub.quizList.repositories.RudaGamesRepository
import ru.quizHub.quizList.repositories.ShakerQuizRepository
import ru.quizHub.quizList.repositories.SmuziRepository
import ru.quizHub.quizList.repositories.SquizRepository
import ru.quizHub.quizList.repositories.WowQuizRepository
import ru.quizHub.quizlist.repository.ICommonRepository
import ru.quizHub.quizlist.repository.IQuizListRepository
import ru.quizHub.quizlist.repository.IQuizPleaseRepository
import ru.quizHub.quizlist.repository.IRudaGamesRepository
import ru.quizHub.quizlist.repository.ISQuizRepository
import ru.quizHub.quizlist.repository.IShakerQuizRepository
import ru.quizHub.quizlist.repository.ISmuziRepository
import ru.quizHub.quizlist.repository.IWowQuizRepository

fun quizDataModule() = module {

    factoryOf(::SquizDataMapper)
    factoryOf(::LocalDataSource)
    factoryOf(::SquizRemoteDataSource)
    singleOf(::SquizRepository) bind ISQuizRepository::class

    factoryOf(::QuizPleaseDataMapper)
    factoryOf(::QuizPleaseRemoteDataSource)
    singleOf(::QuizPleaseRepository) bind IQuizPleaseRepository::class

    factoryOf(::ShakerQuizDataMapper)
    factoryOf(::ShakerQuizRemoteDataSource)
    singleOf(::ShakerQuizRepository) bind IShakerQuizRepository::class

    factoryOf(::RudaGamesDataMapper)
    factoryOf(::RudaGamesRemoteDataSource)
    singleOf(::RudaGamesRepository) bind IRudaGamesRepository::class

    factoryOf(::WowQuizDataMapper)
    factoryOf(::WowQuizRemoteDataSource)
    singleOf(::WowQuizRepository) bind IWowQuizRepository::class

    factoryOf(::SmuziDataMapper)
    factoryOf(::SmuziRemoteDataSource)
    singleOf(::SmuziRepository) bind ISmuziRepository::class

    factoryOf(::CommonDataMapper)
    factoryOf(::GeoInfoRemoteDataSource)
    singleOf(::CommonRepository) bind ICommonRepository::class

    factoryOf(::QuizListLocalDataSource)
    factoryOf(::QuizDBOMapper)
    singleOf(::QuizListRepository) bind IQuizListRepository::class
}
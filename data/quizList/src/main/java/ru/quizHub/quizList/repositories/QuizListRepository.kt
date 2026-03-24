package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import ru.quizHub.quizList.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.quizHub.quizList.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.quizHub.quizList.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.quizHub.quizList.datasource.squiz.SquizRemoteDataSource
import ru.quizHub.quizList.datasource.wowQuiz.WowQuizRemoteDataSource
import ru.quizHub.quizList.mappers.QuizPleaseDataMapper
import ru.quizHub.quizList.mappers.RudaGamesDataMapper
import ru.quizHub.quizList.mappers.ShakerQuizDataMapper
import ru.quizHub.quizList.mappers.SquizDataMapper
import ru.quizHub.quizList.mappers.WowQuizDataMapper
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IQuizListRepository
import timber.log.Timber

/**
 * Агрегирует списки квизов из нескольких источников для выбранного города.
 */
class QuizListRepository(
    private val squizRemoteDataSource: SquizRemoteDataSource,
    private val quizPleaseRemoteDataSource: QuizPleaseRemoteDataSource,
    private val shakerQuizRemoteDataSource: ShakerQuizRemoteDataSource,
    private val rudaGamesRemoteDataSource: RudaGamesRemoteDataSource,
    private val wowQuizRemoteDataSource: WowQuizRemoteDataSource,
    private val squizDataMapper: SquizDataMapper,
    private val quizPleaseDataMapper: QuizPleaseDataMapper,
    private val shakerQuizDataMapper: ShakerQuizDataMapper,
    private val rudaGamesDataMapper: RudaGamesDataMapper,
    private val wowQuizDataMapper: WowQuizDataMapper,
) : IQuizListRepository {

    private companion object {
        const val PAGE_NUMBER = 1
        const val PAGE_SIZE = 100
    }

    override fun getAllQuizList(city: City): Flow<List<Quiz>> {
        val squiz = squizRemoteDataSource.getQuizList(cityId = city.squizId)
            .orEmptyOnFailure(sourceTag = "Squiz")
        val quizPlease = quizPleaseRemoteDataSource.getQuizList(
            cityId = city.quizPleaseId,
            pageNumber = PAGE_NUMBER,
            pageSize = PAGE_SIZE,
        ).orEmptyOnFailure(sourceTag = "QuizPlease")
        val shaker = shakerQuizRemoteDataSource.getQuizList(cityId = city.shakerQuizId)
            .orEmptyOnFailure(sourceTag = "ShakerQuiz")
        val ruda = rudaGamesRemoteDataSource.getQuizList(cityId = city.rudaGamesId)
            .orEmptyOnFailure(sourceTag = "RudaGames")
        val wow = wowQuizRemoteDataSource.getQuizList(
            domain = city.wowQuizDomain,
            page = PAGE_NUMBER,
            upcoming = 1,
        ).orEmptyOnFailure(sourceTag = "WowQuiz")

        return combine(
            squiz,
            quizPlease,
            shaker,
            ruda,
            wow
        ) { squizList,
            quizPleaseList,
            shakerQuizList,
            rudaGamesList,
            wowList ->
            buildList {
                addAll(squizDataMapper.map(quizzes = squizList))
                addAll(quizPleaseDataMapper.mapToQuiz(dtos = quizPleaseList))
                addAll(shakerQuizDataMapper.mapToShakerQuiz(dtos = shakerQuizList))
                addAll(rudaGamesDataMapper.mapToRudaGames(dtos = rudaGamesList))
                addAll(wowQuizDataMapper.mapToWowQuiz(dtos = wowList))
            }
        }
    }

    private fun <T> Flow<List<T>>.orEmptyOnFailure(sourceTag: String): Flow<List<T>> =
        catch { e ->
            Timber.e(e, "QuizListRepository: $sourceTag")
            emit(emptyList())
        }
}

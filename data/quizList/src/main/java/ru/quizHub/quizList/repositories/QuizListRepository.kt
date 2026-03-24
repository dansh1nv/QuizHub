package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _quizListFlow: MutableStateFlow<List<Quiz>> = MutableStateFlow(emptyList())
    override val quizListFlow = _quizListFlow.asStateFlow()

    override fun getAllQuizList(city: City): Flow<List<Quiz>> {
        return combine(
            squizRemoteDataSource.getQuizList(cityId = city.squizId),
            quizPleaseRemoteDataSource.getQuizList(
                cityId = city.quizPleaseId,
                pageNumber = PAGE_NUMBER,
                pageSize = PAGE_SIZE
            ),
            shakerQuizRemoteDataSource.getQuizList(cityId = city.shakerQuizId),
            rudaGamesRemoteDataSource.getQuizList(cityId = city.rudaGamesId),
            wowQuizRemoteDataSource.getQuizList(
                domain = city.wowQuizDomain,
                page = PAGE_NUMBER,
                upcoming = 1,
            ),
        ) { squizList, quizPleaseList, shakerQuizList, rudaGamesList, wowList ->
            listOf(
                squizDataMapper.map(quizzes = squizList),
                quizPleaseDataMapper.mapToQuiz(dtos = quizPleaseList),
                shakerQuizDataMapper.mapToShakerQuiz(dtos = shakerQuizList),
                rudaGamesDataMapper.mapToRudaGames(dtos = rudaGamesList),
                wowQuizDataMapper.mapToWowQuiz(dtos = wowList),
            ).flatten()
        }
    }
}
package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import ru.quizHub.quizList.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.quizHub.quizList.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.quizHub.quizList.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.quizHub.quizList.datasource.squiz.SquizRemoteDataSource
import ru.quizHub.quizList.mappers.QuizPleaseDataMapper
import ru.quizHub.quizList.mappers.RudaGamesDataMapper
import ru.quizHub.quizList.mappers.ShakerQuizDataMapper
import ru.quizHub.quizList.mappers.SquizDataMapper
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IQuizListRepository

class QuizListRepository(
    private val squizRemoteDataSource: SquizRemoteDataSource,
    private val quizPleaseRemoteDataSource: QuizPleaseRemoteDataSource,
    private val shakerQuizRemoteDataSource: ShakerQuizRemoteDataSource,
    private val rudaGamesRemoteDataSource: RudaGamesRemoteDataSource,
    private val squizDataMapper: SquizDataMapper,
    private val quizPleaseDataMapper: QuizPleaseDataMapper,
    private val shakerQuizDataMapper: ShakerQuizDataMapper,
    private val rudaGamesDataMapper: RudaGamesDataMapper
) : IQuizListRepository {

    private companion object {
        const val PAGE_NUMBER = 1
        const val PAGE_SIZE = 100
    }

    private val _quizListFlow: MutableStateFlow<List<Quiz>> = MutableStateFlow(emptyList())
    override val quizListFlow = _quizListFlow.asStateFlow()

    override fun getAllQuizList(city: City): Flow<List<Quiz>> {
        return combine(
            flow = squizRemoteDataSource.getQuizList(cityId = city.squizId),
            flow2 = quizPleaseRemoteDataSource.getQuizList(
                cityId = city.quizPleaseId,
                pageNumber = PAGE_NUMBER,
                pageSize = PAGE_SIZE
            ),
            flow3 = shakerQuizRemoteDataSource.getQuizList(cityId = city.shakerQuizId),
            flow4 = rudaGamesRemoteDataSource.getQuizList(cityId = city.rudaGamesId)
        ) { squizList, quizPleaseList, shakerQuizList, rudaGamesList ->
            listOf(
                squizDataMapper.map(quizzes = squizList),
                quizPleaseDataMapper.mapToQuiz(dtos = quizPleaseList),
                shakerQuizDataMapper.mapToShakerQuiz(dtos = shakerQuizList),
                rudaGamesDataMapper.mapToRudaGames(dtos = rudaGamesList)
            ).flatten()
        }
    }
}
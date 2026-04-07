package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.quizHub.quizList.datasource.QuizListLocalDataSource
import ru.quizHub.quizList.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.quizHub.quizList.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.quizHub.quizList.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.quizHub.quizList.datasource.squiz.SquizRemoteDataSource
import ru.quizHub.quizList.datasource.wowQuiz.WowQuizRemoteDataSource
import ru.quizHub.quizList.mappers.QuizDBOMapper
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
 * Поддерживает локальное кэширование через QuizListLocalDataSource.
 */
class QuizListRepository(
    private val localDataSource: QuizListLocalDataSource,
    private val quizDBOMapper: QuizDBOMapper,
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

    override fun getAllQuizList(city: City, forceRefresh: Boolean): Flow<List<Quiz>> {
        return flow {
            Timber.d("getAllQuizList called for city: ${city.name}, forceRefresh: $forceRefresh")

            // Если не требуется принудительное обновление — сначала пытаемся взять из кэша
            if (!forceRefresh) {
                Timber.d("Trying to load from cache for city: ${city.name}")
                try {
                    val dboList = localDataSource.getQuizzes().first()
                    if (dboList.isNotEmpty()) {
                        val cachedQuizzes = quizDBOMapper.mapToQuizList(dboList)
                        Timber.d("Loaded ${cachedQuizzes.size} quizzes from cache for city: ${city.name}")
                        emit(cachedQuizzes)
                        return@flow
                    }
                    Timber.d("Cache is empty for city: ${city.name}, falling back to remote")
                } catch (e: Exception) {
                    Timber.w(e, "Failed to load cached quizzes for city: ${city.name}")
                }
            }

            // Если кэша нет или требуется обновление — запрашиваем из сети
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

            val remoteFlow = combine(
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

            remoteFlow.collect { quizList ->
                Timber.d("Received ${quizList.size} quizzes from remote sources for city: ${city.name}")
                emit(quizList)

                // Сохраняем в кэш
                if (quizList.isNotEmpty()) {
                    try {
                        val dboList = quizDBOMapper.mapToQuizDBOList(quizList, city.name)
                        localDataSource.saveQuizzes(dboList)
                        Timber.i("Successfully saved ${dboList.size} quizzes to local cache for city: ${city.name}")
                    } catch (e: Exception) {
                        Timber.e(
                            e,
                            "Failed to save ${quizList.size} quizzes to local cache for city: ${city.name}"
                        )
                    }
                } else {
                    Timber.w("Received empty quiz list from remote sources for city: ${city.name}")
                }
            }
        }
    }

    private fun <T> Flow<List<T>>.orEmptyOnFailure(sourceTag: String): Flow<List<T>> =
        catch { e ->
            Timber.e(e, "QuizListRepository: Failed to load from $sourceTag source")
            emit(emptyList())
        }
}

package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.quizHub.quizList.cache.QuizOrganization
import ru.quizHub.quizList.datasource.QuizListLocalDataSource
import ru.quizHub.quizList.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.quizHub.quizList.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.quizHub.quizList.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.quizHub.quizList.datasource.smuzi.SmuziRemoteDataSource
import ru.quizHub.quizList.datasource.squiz.SquizRemoteDataSource
import ru.quizHub.quizList.datasource.wowQuiz.WowQuizRemoteDataSource
import ru.quizHub.quizList.mappers.QuizDBOMapper
import ru.quizHub.quizList.mappers.QuizPleaseDataMapper
import ru.quizHub.quizList.mappers.RudaGamesDataMapper
import ru.quizHub.quizList.mappers.ShakerQuizDataMapper
import ru.quizHub.quizList.mappers.SmuziDataMapper
import ru.quizHub.quizList.mappers.SquizDataMapper
import ru.quizHub.quizList.mappers.WowQuizDataMapper
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IQuizListRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Агрегирует списки квизов из нескольких источников для выбранного города.
 * Кэш: city-scoped, TTL, SWR, partial updates по организации, полный payload карточки.
 */
class QuizListRepository(
    private val localDataSource: QuizListLocalDataSource,
    private val quizDBOMapper: QuizDBOMapper,
    private val squizRemoteDataSource: SquizRemoteDataSource,
    private val quizPleaseRemoteDataSource: QuizPleaseRemoteDataSource,
    private val shakerQuizRemoteDataSource: ShakerQuizRemoteDataSource,
    private val rudaGamesRemoteDataSource: RudaGamesRemoteDataSource,
    private val wowQuizRemoteDataSource: WowQuizRemoteDataSource,
    private val smuziRemoteDataSource: SmuziRemoteDataSource,
    private val squizDataMapper: SquizDataMapper,
    private val quizPleaseDataMapper: QuizPleaseDataMapper,
    private val shakerQuizDataMapper: ShakerQuizDataMapper,
    private val rudaGamesDataMapper: RudaGamesDataMapper,
    private val wowQuizDataMapper: WowQuizDataMapper,
    private val smuziDataMapper: SmuziDataMapper,
) : IQuizListRepository {

    private companion object {
        const val PAGE_NUMBER = 1
        const val PAGE_SIZE = 100
        val CACHE_TTL_MS: Long = TimeUnit.MINUTES.toMillis(45)
    }

    override fun getAllQuizList(city: City, forceRefresh: Boolean): Flow<List<Quiz>> {
        return flow {
            Timber.d("getAllQuizList called for city: ${city.name}, forceRefresh: $forceRefresh")

            val cachedDboList = loadCache(city.name)
            val hasCache = cachedDboList.isNotEmpty()
            if (hasCache) {
                val cachedQuizzes = quizDBOMapper.mapToQuizList(cachedDboList)
                Timber.d("Loaded ${cachedQuizzes.size} quizzes from cache for city: ${city.name}")
                emit(cachedQuizzes)
            }

            val cacheAgeMs = cachedDboList.maxOfOrNull { it.cachedAt }
                ?.let { System.currentTimeMillis() - it }
                ?: Long.MAX_VALUE
            val isCacheFresh = hasCache && cacheAgeMs <= CACHE_TTL_MS

            if (!forceRefresh && isCacheFresh) {
                Timber.d("Cache is fresh for city: ${city.name}, skipping remote")
                return@flow
            }

            merge(
                fetchSource(
                    organization = QuizOrganization.SQUIZ,
                    sourceTag = "Squiz",
                ) {
                    squizRemoteDataSource.getQuizList(cityId = city.squizId)
                        .map(squizDataMapper::map)
                },
                fetchSource(
                    organization = QuizOrganization.QUIZ_PLEASE,
                    sourceTag = "QuizPlease",
                ) {
                    quizPleaseRemoteDataSource.getQuizList(
                        cityId = city.quizPleaseId,
                        pageNumber = PAGE_NUMBER,
                        pageSize = PAGE_SIZE,
                    ).map(quizPleaseDataMapper::mapToQuiz)
                },
                fetchSource(
                    organization = QuizOrganization.SHAKER,
                    sourceTag = "ShakerQuiz",
                ) {
                    shakerQuizRemoteDataSource.getQuizList(cityId = city.shakerQuizId)
                        .map(shakerQuizDataMapper::mapToShakerQuiz)
                },
                fetchSource(
                    organization = QuizOrganization.RUDA,
                    sourceTag = "RudaGames",
                ) {
                    rudaGamesRemoteDataSource.getQuizList(cityId = city.rudaGamesId)
                        .map(rudaGamesDataMapper::mapToRudaGames)
                },
                fetchSource(
                    organization = QuizOrganization.WOW,
                    sourceTag = "WowQuiz",
                ) {
                    wowQuizRemoteDataSource.getQuizList(
                        domain = city.wowQuizDomain,
                        page = PAGE_NUMBER,
                        upcoming = 1,
                    ).map(wowQuizDataMapper::mapToWowQuiz)
                },
                fetchSource(
                    organization = QuizOrganization.SMUZI,
                    sourceTag = "Smuzi",
                ) {
                    smuziRemoteDataSource.getQuizList(storePartId = city.smuziStorePartId)
                        .map(smuziDataMapper::mapToSmuzi)
                },
            ).collect { sourceResult ->
                when (sourceResult) {
                    is SourceResult.Success -> {
                        try {
                            val dboList = quizDBOMapper.mapToQuizDBOList(sourceResult.quizzes, city.name)
                            localDataSource.replaceByCityAndOrganization(
                                city = city.name,
                                organization = sourceResult.organization,
                                quizzes = dboList,
                            )
                            Timber.i(
                                "Cached ${dboList.size} quizzes for ${sourceResult.organization} in ${city.name}"
                            )
                        } catch (e: Exception) {
                            Timber.e(
                                e,
                                "Failed to cache ${sourceResult.organization} for ${city.name}"
                            )
                        }
                        emit(quizDBOMapper.mapToQuizList(loadCache(city.name)))
                    }

                    is SourceResult.Failure -> {
                        Timber.w(
                            "Keeping cached ${sourceResult.organization} for ${city.name} after remote failure"
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadCache(cityName: String) = try {
        localDataSource.getQuizzesByCity(cityName).first()
    } catch (e: Exception) {
        Timber.w(e, "Failed to load cached quizzes for city: $cityName")
        emptyList()
    }

    private fun fetchSource(
        organization: String,
        sourceTag: String,
        block: () -> Flow<List<Quiz>>,
    ): Flow<SourceResult> =
        block()
            .map<List<Quiz>, SourceResult> { quizzes ->
                SourceResult.Success(organization = organization, quizzes = quizzes)
            }
            .catch { e ->
                Timber.e(e, "QuizListRepository: Failed to load from $sourceTag source")
                emit(SourceResult.Failure(organization = organization))
            }

    private sealed interface SourceResult {
        val organization: String

        data class Success(
            override val organization: String,
            val quizzes: List<Quiz>,
        ) : SourceResult

        data class Failure(
            override val organization: String,
        ) : SourceResult
    }
}

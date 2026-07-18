package ru.quizHub.quizList.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.quizHub.quizlist.repository.ICommonRepository
import ru.quizHub.quizlist.repository.IQuizListRepository
import timber.log.Timber

/**
 * Фоновое обновление кэша квизов для текущего города.
 */
class QuizCacheRefreshWorker(
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {

    private val commonRepository: ICommonRepository by inject()
    private val quizListRepository: IQuizListRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            val cityName = commonRepository.getCurrentCity().first()
            if (cityName.isBlank()) {
                Timber.d("QuizCacheRefreshWorker: no current city, skip")
                return Result.success()
            }

            val cities = commonRepository.fetchCities().first()
            val city = cities.firstOrNull { it.name == cityName }
            if (city == null) {
                Timber.w("QuizCacheRefreshWorker: city '$cityName' not found in cities list")
                return Result.success()
            }

            Timber.d("QuizCacheRefreshWorker: refreshing cache for $cityName")
            quizListRepository.getAllQuizList(city = city, forceRefresh = true).lastOrNull()
            Timber.i("QuizCacheRefreshWorker: cache refreshed for $cityName")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "QuizCacheRefreshWorker failed")
            Result.retry()
        }
    }
}
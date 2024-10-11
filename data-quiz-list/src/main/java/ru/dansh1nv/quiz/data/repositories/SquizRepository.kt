package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.squiz.SquizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.SquizMapper
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.repository.ISQuizRepository

class SquizRepository(
//    private val localDataSource: LocalDataSource,
    private val remoteDataSource: SquizRemoteDataSource,
    private val quizMapper: SquizMapper,
) : ISQuizRepository {

    override suspend fun getAllQuizzes(): Flow<List<SQuiz>> {
        return remoteDataSource.getAllQuizzes()
            .map(quizMapper::map)
    }

}

//internal fun <T : Any> RequestResult<T?>.requireData(): T = checkNotNull(data)
//
//internal fun <I, O> RequestResult<I>.map(
//    mapper: (I) -> O
//): RequestResult<O> {
//    val outData = mapper(data)
//    return when (this) {
//        is RequestResult.Success -> RequestResult.Success(outData)
//        is RequestResult.Error -> RequestResult.Error(outData)
//        is RequestResult.InProgress -> RequestResult.InProgress(outData)
//    }
//}
package ru.quizHub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.quizHub.database.models.QuizDBO

@Dao
interface QuizDao {

    @Query("SELECT * FROM quizzes")
    fun getAll(): Flow<List<QuizDBO>>

    @Query("SELECT * FROM quizzes WHERE city = :city")
    fun getByCity(city: String): Flow<List<QuizDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quizList: List<QuizDBO>)

    @Query("DELETE FROM quizzes WHERE city = :city")
    suspend fun deleteByCity(city: String)

    @Query("DELETE FROM quizzes WHERE city = :city AND organization = :organization")
    suspend fun deleteByCityAndOrganization(city: String, organization: String)

    @Query("DELETE FROM quizzes")
    suspend fun clean()
}

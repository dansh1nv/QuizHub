package ru.dansh1nv.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.database.models.QuizDBO

@Dao
interface QuizDao {

    @Query("SELECT * FROM quizzes")
    fun getAll(): Flow<List<QuizDBO>>

    @Insert
    suspend fun insert(quizList: List<QuizDBO>)

    @Delete
    suspend fun remove(quizList: List<QuizDBO>)

    @Query("DELETE FROM quizzes")
    suspend fun clean()

}
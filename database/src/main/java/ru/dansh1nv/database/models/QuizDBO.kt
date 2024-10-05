package ru.dansh1nv.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizDBO(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("organization") val organization: String,
    @ColumnInfo("city") val city: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("format") val format: String,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("theme") val theme: String,
    @ColumnInfo("packageNumber") val packageNumber: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("place") val place: String,
    @ColumnInfo("time") val time: String,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("price") val price: String,
)
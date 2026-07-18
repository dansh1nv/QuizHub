package ru.quizHub.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object QuizDatabaseMigrations {

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE quizzes ADD COLUMN payload TEXT NOT NULL DEFAULT ''"
            )
        }
    }

    val ALL = arrayOf(MIGRATION_3_4)
}
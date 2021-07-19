package com.appscrip.trivia.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appscrip.trivia.data.db.converter.ArrayListConverter
import com.appscrip.trivia.data.db.dao.QuestionDao
import com.appscrip.trivia.data.db.entity.Question
import com.appscrip.trivia.util.DATABASE_VERSION

/**
 * AppDatabase manages all the database configuration and transaction.
 */
@Database(
    entities = [Question::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(ArrayListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): QuestionDao
}
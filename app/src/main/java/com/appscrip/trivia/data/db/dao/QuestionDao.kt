package com.appscrip.trivia.data.db.dao

import androidx.room.*
import com.appscrip.trivia.data.db.entity.Question
import kotlinx.coroutines.flow.Flow

/**
 * QuestionDao manages all the database queries.
 */
@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(questions: List<Question>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(question: Question): Long

    @Query("SELECT * FROM question")
    fun fetchIssues(): Flow<List<Question>>

    @Update
    suspend fun update(question: Question)
}
package com.appscrip.trivia.data.repository

import com.appscrip.trivia.data.db.entity.Question
import kotlinx.coroutines.flow.Flow

/**
 * IssueRepository manages the transactions of data layer and network layer.
 */
interface QuestionRepository {

    /**
     * Fetch the data from local database
     */
    fun fetchDataFromDB(): Flow<List<Question>>

    /**
     * Update the issue entity in the database.
     */
    suspend fun update(question: Question)

    /**
     * Insert the list of question in the database.
     */
    suspend fun insert(data: List<Question>): LongArray

    /**
     * Insert the model of questions in the database.
     */
    suspend fun insert(data: Question): Long
}
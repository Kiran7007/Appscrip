package com.appscrip.trivia.data.repository

import com.appscrip.trivia.data.db.dao.QuestionDao
import com.appscrip.trivia.data.db.entity.Question

/**
 * IssueRepositoryImpl responsible for doing database and network transactions.
 */
class QuestionRepositoryImpl(
    private val questionDao: QuestionDao
) : QuestionRepository {

    companion object {
        private val TAG = QuestionRepository::class.java.simpleName
    }

    override fun fetchDataFromDB() = questionDao.fetchIssues()

    override suspend fun update(question: Question) = questionDao.update(question)

    override suspend fun insert(data: List<Question>) = questionDao.insert(data)

    override suspend fun insert(data: Question) = questionDao.insert(data)
}
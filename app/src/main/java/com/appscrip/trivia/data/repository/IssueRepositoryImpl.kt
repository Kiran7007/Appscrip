package com.appscrip.trivia.data.repository

import com.appscrip.trivia.data.db.dao.IssueDao
import com.appscrip.trivia.data.db.entity.Issue

/**
 * IssueRepositoryImpl responsible for doing database and network transactions.
 */
class IssueRepositoryImpl(
    private val issueDao: IssueDao
) : IssueRepository {

    companion object {
        private val TAG = IssueRepository::class.java.simpleName
    }

    override fun fetchDataFromDB() = issueDao.fetchIssues()

    override suspend fun update(issue: Issue) = issueDao.update(issue)

    override suspend fun insert(data: List<Issue>) = issueDao.insert(data)
}
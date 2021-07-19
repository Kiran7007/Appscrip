package com.appscrip.trivia.data.repository

import com.appscrip.trivia.data.db.dao.CommentDao
import com.appscrip.trivia.data.db.entity.Comment

/**
 * IssueRepositoryImpl responsible for doing database and network transactions.
 */
class CommentRepositoryImpl(
    private val commentDao: CommentDao
) : CommentRepository {

    companion object {
        private val TAG = IssueRepository::class.java.simpleName
    }

    override fun fetchDataFromDB() = commentDao.fetchComments()

    override suspend fun update(comment: Comment) = commentDao.update(comment)

    override suspend fun insert(data: List<Comment>) = commentDao.insert(data)
}
package com.appscrip.trivia.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appscrip.trivia.data.db.dao.CommentDao
import com.appscrip.trivia.data.db.dao.IssueDao
import com.appscrip.trivia.data.db.entity.Comment
import com.appscrip.trivia.data.db.entity.Issue
import com.appscrip.trivia.util.DATABASE_VERSION

/**
 * AppDatabase manages all the database configuration and transaction.
 */
@Database(
    entities = [Issue::class, Comment::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao
    abstract fun commentDao(): CommentDao
}
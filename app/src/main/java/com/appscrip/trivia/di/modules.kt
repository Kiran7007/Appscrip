package com.appscrip.trivia.di

import androidx.room.Room
import com.appscrip.trivia.data.db.AppDatabase
import com.appscrip.trivia.data.repository.CommentRepository
import com.appscrip.trivia.data.repository.CommentRepositoryImpl
import com.appscrip.trivia.data.repository.IssueRepository
import com.appscrip.trivia.data.repository.IssueRepositoryImpl
import com.appscrip.trivia.ui.comment.CommentViewModel
import com.appscrip.trivia.ui.issue.IssueViewModel
import com.appscrip.trivia.util.DATABASE_NAME
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val databaseModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.issueDao()
    }

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.commentDao()
    }
}

val repositoryModules = module {
    single<IssueRepository> { IssueRepositoryImpl(get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
}

val viewModelModules = module {
    viewModel { IssueViewModel(get()) }
    viewModel { CommentViewModel(get()) }
}


package com.appscrip.trivia.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.appscrip.trivia.data.db.AppDatabase
import com.appscrip.trivia.data.repository.QuestionRepository
import com.appscrip.trivia.data.repository.QuestionRepositoryImpl
import com.appscrip.trivia.ui.questions.QuestionViewModel
import com.appscrip.trivia.util.DATABASE_NAME
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import java.util.concurrent.Executors

val databaseModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.issueDao()
    }
}

val repositoryModules = module {
    single<QuestionRepository> { QuestionRepositoryImpl(get()) }
}

val viewModelModules = module {
    viewModel { QuestionViewModel(get()) }
}


package com.appscrip.trivia

import androidx.multidex.MultiDexApplication
import com.appscrip.trivia.di.databaseModules
import com.appscrip.trivia.di.repositoryModules
import com.appscrip.trivia.di.viewModelModules
import org.koin.android.ext.android.startKoin

class AppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Configure the koin dependency injection.
        startKoin(
            this, listOf(
                databaseModules,
                repositoryModules,
                viewModelModules
            )
        )
    }
}
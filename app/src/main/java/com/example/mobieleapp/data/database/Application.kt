package com.example.mobieleapp.data.database

import android.app.Application
import com.example.mobieleapp.data.database.user.UserRepository
import com.example.mobieleapp.data.database.wordbrol.WordRepository
import com.example.mobieleapp.data.database.wordbrol.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Application : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val databaseWord by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repositoryWord by lazy { WordRepository(databaseWord.wordDao()) }
    val databaseUser by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val repositoryUser by lazy { UserRepository(databaseUser.userDao()) }

}
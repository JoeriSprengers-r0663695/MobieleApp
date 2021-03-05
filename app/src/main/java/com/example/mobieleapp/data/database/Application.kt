package com.example.mobieleapp.data.database

import android.app.Application
import com.example.mobieleapp.data.database.dorm.DormRepository
import com.example.mobieleapp.data.database.user.UserRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Application : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val roomDatabase by lazy { RoomDatabase.getDatabase(this, applicationScope) }
    val repositoryUser by lazy { UserRepository(roomDatabase.userDao()) }
    val repositoryDorm by lazy { DormRepository(roomDatabase.dormDao())}

}
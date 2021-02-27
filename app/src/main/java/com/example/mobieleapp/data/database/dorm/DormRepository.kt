package com.example.mobieleapp.data.database.dorm

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DormRepository(private val dormDao: DormDao) {
    val allDorms: Flow<List<Dorm>> = dormDao.getAllDorms()
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dorm : Dorm) {
        dormDao.insert(dorm)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        dormDao.deleteAll()
    }
}
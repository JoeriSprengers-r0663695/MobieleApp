package com.example.mobieleapp.data.database.dorm

import kotlinx.coroutines.flow.Flow

class DormRepository(private val dormDao: DormDao) {
    val allDorms: Flow<List<Dorm>> = dormDao.getAllDorms()




    suspend fun dormForUser(id: Int): List<Dorm> {
        return dormDao.dormForUser(id)
    }

    suspend fun insert(dorm : Dorm) {
        dormDao.insert(dorm)
    }

    suspend fun deleteAll() {
        dormDao.deleteAll()
    }

    suspend fun deleteSpecific(dorm: Dorm) {
        dormDao.delete(dorm)
    }

    suspend fun updateDorm(dorm: Dorm) {
        dormDao.updateDorm(dorm)
    }


}
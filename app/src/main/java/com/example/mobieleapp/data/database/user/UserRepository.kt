package com.example.mobieleapp.data.database.user

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mobieleapp.data.database.dorm.Dorm
import kotlinx.coroutines.flow.Flow


class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<List<User>> = userDao.getAllUser()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.


     suspend fun userByid(id : Int) : User {
        return userDao.getUserById(id)
    }

    suspend fun insert(user :User) {
        userDao.insert(user)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }

}
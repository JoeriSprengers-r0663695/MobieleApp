package com.example.mobieleapp.data.database.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobieleapp.data.database.dorm.Dorm
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface UserDao {

        @androidx.room.Query("SELECT * FROM user_table ORDER BY username ASC")
        fun getAllUser(): Flow<List<User>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(User: User)

        @androidx.room.Query("DELETE FROM user_table")
        suspend fun deleteAll()

        @androidx.room.Query("SELECT * FROM user_table WHERE idUser= :id")
        suspend fun getUserById(id: Int): User
}
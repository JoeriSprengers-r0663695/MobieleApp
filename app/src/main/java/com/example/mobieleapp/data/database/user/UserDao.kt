package com.example.mobieleapp.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.mobieleapp.data.database.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

        @androidx.room.Query("SELECT * FROM user_table ORDER BY username ASC")
        fun getAllUser(): Flow<List<User>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(User: User)

        @androidx.room.Query("DELETE FROM user_table")
        suspend fun deleteAll()
}
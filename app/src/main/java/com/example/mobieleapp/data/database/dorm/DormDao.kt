package com.example.mobieleapp.data.database.dorm

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface DormDao {

    @androidx.room.Query("SELECT * FROM dorm_table ORDER BY streetname ASC")
    fun getAllDorms(): Flow<List<Dorm>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(Dorm: Dorm)

    @androidx.room.Query("DELETE FROM dorm_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(dorm: Dorm)

    @Update
    suspend fun updateDorm(dorm: Dorm)


}
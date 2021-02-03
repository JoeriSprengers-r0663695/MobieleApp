package com.example.mobieleapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

@Dao
interface WordDao {

    @androidx.room.Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @androidx.room.Query("DELETE FROM word_table")
    suspend fun deleteAll()

}
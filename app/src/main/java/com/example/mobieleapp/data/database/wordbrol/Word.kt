package com.example.mobieleapp.data.database.wordbrol

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey (autoGenerate = true)
    val id  : Int? = null,

    @ColumnInfo(name = "word")val word: String?,
    @ColumnInfo(name = "word2") val word2: String?

)



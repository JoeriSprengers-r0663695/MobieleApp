package com.example.mobieleapp.data.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey (autoGenerate = true)
    val id  : Int? = null,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name="password") val password: String?,
    @ColumnInfo(name= "role") val role: String?
    // @ColumnInfo(name= "profilePicture") val pic: Data?
)


package com.example.mobieleapp.data.database.dorm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dorm_table")
class Dorm(
    @PrimaryKey (autoGenerate = true)
    val id  : Int? = null,
    @ColumnInfo(name = "streetname") val streetname: String?,
    @ColumnInfo(name = "housenr") val housenr: Int?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "postcode") val postcode: Int?,
    @ColumnInfo(name = "rent") val rent: Float?,
    @ColumnInfo(name = "description") val description: String?,
    )
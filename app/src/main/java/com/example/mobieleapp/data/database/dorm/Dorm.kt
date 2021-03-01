package com.example.mobieleapp.data.database.dorm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mobieleapp.data.database.user.User
import java.io.Serializable

@Entity(tableName = "dorm_table")
class Dorm(
    @PrimaryKey (autoGenerate = true)
    val idDorm: Int? = null,
    @ColumnInfo(name = "adTitle") val adTitle: String?,
    @ColumnInfo(name = "streetname") val streetname: String?,
    @ColumnInfo(name = "housenr") val housenr: Int?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "postalcode") val postalcode: Int?,
    @ColumnInfo(name = "rent") val rent: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ForeignKey(entity = User::class, parentColumns = ["idDorm"], childColumns = ["username"], onDelete = ForeignKey.CASCADE) val idUser: Int

    ):Serializable
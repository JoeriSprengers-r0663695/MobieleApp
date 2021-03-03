package com.example.mobieleapp.data.database.dorm

import androidx.room.*
import com.example.mobieleapp.data.database.user.User
import java.io.Serializable

@Entity(tableName = "dorm_table")
data class Dorm(
    @PrimaryKey (autoGenerate = true)
    val idDorm: Int? = null,
    @ColumnInfo(name = "adTitle") val adTitle: String?,
    @ColumnInfo(name = "streetname") val streetname: String?,
    @ColumnInfo(name = "housenr") val housenr: Int?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "postalcode") val postalcode: Int?,
    @ColumnInfo(name = "rent") val rent: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "owner") val  idUser: Int?,

    ):Serializable


data class UserHasDorms(
    @Embedded val user: User,
    @Relation(
        parentColumn = "idUser",
        entityColumn = "owner"
    )
    val dorms: List<Dorm>


)
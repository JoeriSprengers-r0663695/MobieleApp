package com.example.mobieleapp.data.database.dorm

import androidx.room.*
import com.example.mobieleapp.data.database.user.User
import java.io.Serializable

@Entity(tableName = "dorm_table")
data class Dorm(
    @PrimaryKey
    val adTitle: String,
    @ColumnInfo(name = "streetname") val streetname: String?,
    @ColumnInfo(name = "housenr") val housenr: Long?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "postalcode") val postalcode: Long?,
    @ColumnInfo(name = "rent") val rent: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "owner") val  User: String?,

    ):Serializable


data class UserHasDorms(
    @Embedded val user: User,
    @Relation(
        parentColumn = "idUser",
        entityColumn = "owner"
    )
    val dorms: List<Dorm>


)
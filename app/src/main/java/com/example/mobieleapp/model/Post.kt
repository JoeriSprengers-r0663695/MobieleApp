package com.example.myfirstapp.model

import java.lang.reflect.Array

data class Post (
        //Entire data object from api
        val data: List<Data>
) {
    data class Data(
            val administrative_area: String,
            val confidence: Int,
            val continent: String,
            val country: String,
            val country_code: String,
            val county: String,
            val label: String,
            val latitude: Double,
            val locality: String,
            val longitude: Double,
            val name: String,
            val neighbourhood: Any,
            val number: String,
            val postal_code: String,
            val region: String,
            val region_code: String,
            val street: String,
            val type: String
    )
}
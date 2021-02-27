package com.example.myfirstapp.repository

import com.example.myfirstapp.api.RetrofitInstance
import com.example.mobieleapp.data.model.Post
import retrofit2.Response

class Repository {

    suspend fun getPost(adres : String): Response<Post> {
        return RetrofitInstance.api.getPost(adres = adres, limit = 1)
    }
}
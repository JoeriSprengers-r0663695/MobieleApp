package com.example.myfirstapp.api

import android.util.Log
import com.example.myfirstapp.model.Post
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    //more info : https://johncodeos.com/how-to-make-post-get-put-and-delete-requests-with-retrofit-using-kotlin/



    @GET("v1/forward?access_key=005ee2992bd30070e603ac1014c2972d")
    suspend fun getPost(
            @Query("query") adres : String
    ): Response<Post>

}


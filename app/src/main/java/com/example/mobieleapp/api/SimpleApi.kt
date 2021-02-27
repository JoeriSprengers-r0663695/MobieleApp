package com.example.myfirstapp.api

import com.example.mobieleapp.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    //more info : https://johncodeos.com/how-to-make-post-get-put-and-delete-requests-with-retrofit-using-kotlin/



    @GET("v1/forward?access_key=005ee2992bd30070e603ac1014c2972d")
    suspend fun getPost(
            @Query("query") adres : String,
            @Query("limit") limit : Int
    ): Response<Post>

}


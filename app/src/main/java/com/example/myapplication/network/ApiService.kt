package com.example.myapplication.network

import com.example.myapplication.models.Credentials
import com.example.myapplication.models.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.myapplication.models.Tap
import retrofit2.http.GET
import retrofit2.http.Header
interface ApiService {
    @POST("login")
    suspend fun login(@Body credentials: Credentials): Response<Token>

    @GET("taps")
    suspend fun getTaps(@Header("Authorization") token: String): Response<List<Tap>>
}
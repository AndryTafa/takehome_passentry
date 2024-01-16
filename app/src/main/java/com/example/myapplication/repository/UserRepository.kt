package com.example.myapplication.repository

import com.example.myapplication.network.ApiService
import com.example.myapplication.models.Token
import com.example.myapplication.models.Credentials
import retrofit2.Response
import javax.inject.Inject
import com.example.myapplication.models.Tap

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(credentials: Credentials): Response<Token> {
        return apiService.login(credentials)
    }

    suspend fun getTaps(token: String): Response<List<Tap>> {
        return apiService.getTaps("Bearer $token")
    }
}
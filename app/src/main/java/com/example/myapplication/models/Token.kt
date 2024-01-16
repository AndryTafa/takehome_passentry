package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("api-token")
    val apiToken: String?
)
package com.example.myapplication.presentation.states

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val token: String) : UserState()
    data class Error(val message: String) : UserState()
}
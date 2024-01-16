package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.models.Credentials
import com.example.myapplication.models.Tap
import com.example.myapplication.presentation.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userState = MutableLiveData<UserState>()
    val userState: LiveData<UserState> get() = _userState

    private val _bearerToken = MutableLiveData<String>()
    val bearerToken: LiveData<String> get() = _bearerToken

    private val _tapsData = MutableLiveData<List<Tap>>()
    val tapsData: LiveData<List<Tap>> get() = _tapsData

    fun loginUser(credentials: Credentials) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(credentials)
                if (response.isSuccessful && response.body() != null) {
                    _bearerToken.value = response.body()!!.apiToken ?: ""
                    _userState.value = UserState.Success(_bearerToken.value ?: "")
                } else {
                    _userState.value = UserState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Exception occurred: ${e.localizedMessage}")
            }
        }
    }

    fun fetchTaps(token: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.getTaps(token)
                if (response.isSuccessful && response.body() != null) {
                    _tapsData.value = response.body()
                } else {

                }
            } catch (e: Exception) {
            }
        }
    }
}
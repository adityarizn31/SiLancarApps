package com.example.silancarapps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silancarapps.data.local.User
import com.example.silancarapps.data.repository.PendaftaranRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: PendaftaranRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _loginUser = MutableLiveData<User?>()
    val loginUser: LiveData<User?> = _loginUser

    fun register(user: User) {
        viewModelScope.launch {
            try {
                val result = repository.registerUser(user)
                android.util.Log.d("AUTH_DEBUG", "Register result ID: $result")
                _registerResult.value = (result != -1L)
            } catch (e: Exception) {
                android.util.Log.e("AUTH_DEBUG", "Register error: ${e.message}")
                _registerResult.value = false
            }
        }
    }

    fun login(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            _loginUser.postValue(user)
        }
    }
}

package com.example.silancarapps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silancarapps.data.local.User
import com.example.silancarapps.data.repository.PengajuanRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: PengajuanRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _loginUser = MutableLiveData<User?>()
    val loginUser: LiveData<User?> = _loginUser

    fun register(user: User) {
        viewModelScope.launch {
            val result = repository.registerUser(user)
            _registerResult.postValue(result != -1L)
        }
    }

    fun login(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            _loginUser.postValue(user)
        }
    }
}

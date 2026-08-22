package com.example.silancarapps.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("SiLancarPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGIN = "isLogin"
        private const val USER_NAME = "userName"
        private const val USER_EMAIL = "userEmail"
    }

    fun saveSession(name: String, email: String) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(USER_NAME, name)
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }

    fun isLogin(): Boolean = prefs.getBoolean(IS_LOGIN, false)

    fun getUserName(): String? = prefs.getString(USER_NAME, "User")
    
    fun getUserEmail(): String? = prefs.getString(USER_EMAIL, "")

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}

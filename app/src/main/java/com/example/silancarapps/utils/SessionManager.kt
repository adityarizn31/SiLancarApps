package com.example.silancarapps.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("SiLancarPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGIN = "isLogin"
        private const val USER_NAME = "userName"
        private const val USER_EMAIL = "userEmail"
        private const val USER_NIK = "userNik"
        private const val USER_PHONE = "userPhone"
    }

    fun saveSession(name: String, email: String, nik: String? = null, phone: String? = null) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(USER_NAME, name)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_NIK, nik)
        editor.putString(USER_PHONE, phone)
        editor.apply()
    }

    fun isLogin(): Boolean = prefs.getBoolean(IS_LOGIN, false)

    fun getUserName(): String? = prefs.getString(USER_NAME, "User")
    
    fun getUserEmail(): String? = prefs.getString(USER_EMAIL, "")

    fun getUserNik(): String? = prefs.getString(USER_NIK, null)

    fun getUserPhone(): String? = prefs.getString(USER_PHONE, null)

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}

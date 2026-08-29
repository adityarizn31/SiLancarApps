package com.example.silancarapps.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.silancarapps.data.local.AppDatabase
import com.example.silancarapps.data.repository.PengajuanRepository

class ViewModelFactory(private val repository: PengajuanRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PengajuanViewModel::class.java) -> {
                PengajuanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val database = AppDatabase.getDatabase(context)
                val repository = PengajuanRepository(
                    database.pengajuanAktaKelahiranDao(),
                    database.pengajuanAktaKematianDao(),
                    database.pengajuanKIADao(),
                    database.pengajuanKKDao(),
                    database.pengajuanKTPDao(),
                    database.userDao()
                )
                ViewModelFactory(repository).also { INSTANCE = it }
            }
        }
    }
}

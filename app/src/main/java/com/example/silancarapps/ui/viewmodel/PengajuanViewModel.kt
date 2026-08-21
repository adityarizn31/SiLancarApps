package com.example.silancarapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.data.repository.PengajuanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PengajuanViewModel(private val repository: PengajuanRepository) : ViewModel() {

    val allPengajuanKK: Flow<List<PengajuanKK>> = repository.getAllPengajuanKK()
    val allPengajuanKTP: Flow<List<PengajuanKTP>> = repository.getAllPengajuanKTP()
    val allAktaKelahiran: Flow<List<PengajuanAktaKelahiran>> = repository.getAllAktaKelahiran()
    val allAktaKematian: Flow<List<PengajuanAktaKematian>> = repository.getAllAktaKematian()

    fun insertKK(pengajuanInsertKK: PengajuanKK) {
        viewModelScope.launch {
            repository.insertKK(pengajuanInsertKK)
        }
    }

    fun deleteKK(pengajuanDeleteKK: PengajuanKK) {
        viewModelScope.launch {
            repository.deleteKK(pengajuanDeleteKK)
        }
    }

    fun insertKTP(pengajuanInsertKTP: PengajuanKTP) {
        viewModelScope.launch {
            repository.insertKTP(pengajuanInsertKTP)
        }
    }

    fun deleteKTP(pengajuanDeleteKTP : PengajuanKTP) {
        viewModelScope.launch {
            repository.deleteKTP(pengajuanDeleteKTP)
        }
    }

    fun insertAktaKelahiran(pengajuanAktaKelahiran: PengajuanAktaKelahiran) {
        viewModelScope.launch {
            repository.insertAktaKelahiran(pengajuanAktaKelahiran)
        }
    }

    fun deleteAktaKelahiran(pengajuanDeleteAktaKelahiran : PengajuanAktaKelahiran) {
        viewModelScope.launch {
            repository.deleteAktaKelahiran(pengajuanDeleteAktaKelahiran)
        }
    }

    fun insertAktaKematian(pengajuanAktaKematian: PengajuanAktaKematian) {
        viewModelScope.launch {
            repository.insertAktaKematian(pengajuanAktaKematian)
        }
    }

    fun deleteAktaKematian(pengajuanAktaKematian: PengajuanAktaKematian) {
        viewModelScope.launch {
            repository.deleteAktaKematian(pengajuanAktaKematian)
        }
    }
}

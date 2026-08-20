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

    fun insertKK(pengajuan: PengajuanKK) {
        viewModelScope.launch {
            repository.insertKK(pengajuan)
        }
    }

    fun deleteKK(pengajuan: PengajuanKK) {
        viewModelScope.launch {
            repository.deleteKK(pengajuan)
        }
    }

    fun insertKTP(pengajuan: PengajuanKTP) {
        viewModelScope.launch {
            repository.insertKTP(pengajuan)
        }
    }

    fun insertAktaKelahiran(pengajuan: PengajuanAktaKelahiran) {
        viewModelScope.launch {
            repository.insertAktaKelahiran(pengajuan)
        }
    }

    fun insertAktaKematian(pengajuan: PengajuanAktaKematian) {
        viewModelScope.launch {
            repository.insertAktaKematian(pengajuan)
        }
    }
}

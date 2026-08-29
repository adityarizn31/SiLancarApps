package com.example.silancarapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.data.local.PengajuanKIA
import com.example.silancarapps.data.model.RiwayatPendaftaran
import com.example.silancarapps.data.repository.PendaftaranRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PendaftaranViewModel(private val repository: PendaftaranRepository) : ViewModel() {

    val allAktaKelahiran: Flow<List<PengajuanAktaKelahiran>> = repository.getAllAktaKelahiran()
    val allAktaKematian: Flow<List<PengajuanAktaKematian>> = repository.getAllAktaKematian()
    val allKIA: Flow<List<PengajuanKIA>> = repository.getAllKIA()
    val allPengajuanKK: Flow<List<PengajuanKK>> = repository.getAllPengajuanKK()
    val allPengajuanKTP: Flow<List<PengajuanKTP>> = repository.getAllPengajuanKTP()

    val riwayatPendaftaran: Flow<List<RiwayatPendaftaran>> = combine(
        allAktaKelahiran,
        allAktaKematian,
        allKIA,
        allPengajuanKK,
        allPengajuanKTP,
    ) { aktaLahir, aktaMati, kia, kk, ktp ->
        val list = mutableListOf<RiwayatPendaftaran>()

        aktaLahir.forEach {
            list.add(RiwayatPendaftaran(it.id, it.jenisLayanan, it.namaAnak, it.status, it.tanggal, it))
        }
        aktaMati.forEach {
            list.add(RiwayatPendaftaran(it.id, it.jenisLayanan, it.namaAlm, it.status, it.tanggal, it))
        }
        kia.forEach {
            list.add(RiwayatPendaftaran(it.id, it.jenisLayanan, it.namaLengkapAnak, it.status, it.tanggal, it))
        }
        kk.forEach {
            list.add(RiwayatPendaftaran(it.id, it.jenisLayanan, it.nama, it.status, it.tanggal, it))
        }
        ktp.forEach { 
            list.add(RiwayatPendaftaran(it.id, it.jenisLayanan, it.nama, it.status, it.tanggal, it))
        }
        
        list.sortByDescending { it.tanggal }
        list
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

    fun insertKIA(pengajuanKIA: PengajuanKIA) {
        viewModelScope.launch {
            repository.insertKIA(pengajuanKIA)
        }
    }

    fun deleteKIA(pengajuanKIA: PengajuanKIA) {
        viewModelScope.launch {
            repository.deleteKIA(pengajuanKIA)
        }
    }

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

}

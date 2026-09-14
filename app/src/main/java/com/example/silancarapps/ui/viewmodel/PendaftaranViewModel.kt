package com.example.silancarapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.data.local.PengajuanKIA
import com.example.silancarapps.data.local.PengajuanPelayananPemanfaatanData
import com.example.silancarapps.data.local.PengajuanSuratPindah
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
    val allPengajuanPelayananPemanfaatanData : Flow<List<PengajuanPelayananPemanfaatanData>> = repository.getAllPengajuanPelayananPemanfaatanData()

    val allPengajuanSuratPindah : Flow<List<PengajuanSuratPindah>> = repository.getAllSuratPindah()

    val riwayatPendaftaran: Flow<List<RiwayatPendaftaran>> = combine(
        allAktaKelahiran,
        allAktaKematian,
        allKIA,
        allPengajuanKK,
        allPengajuanKTP,
        allPengajuanPelayananPemanfaatanData,
        allPengajuanSuratPindah,
    ) { arrays: Array<List<Any>> ->
        val list = mutableListOf<RiwayatPendaftaran>()
        
        val aktaLahir = arrays[0] as List<PengajuanAktaKelahiran>
        val aktaMati = arrays[1] as List<PengajuanAktaKematian>
        val kia = arrays[2] as List<PengajuanKIA>
        val kk = arrays[3] as List<PengajuanKK>
        val ktp = arrays[4] as List<PengajuanKTP>
        val pelayanPemanfaatanData = arrays[5] as List<PengajuanPelayananPemanfaatanData>
        val suratPindah = arrays[6] as List<PengajuanSuratPindah>

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
        pelayanPemanfaatanData.forEach {
            list.add(RiwayatPendaftaran(it.id, it.jenisLayanan, it.namaInstansi, it.status, it.tanggal, it))
        }
        suratPindah.forEach {
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

    suspend fun getAktaKelahiranById(id: Int) = repository.getAktaKelahiranById(id)

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

    suspend fun getAktaKematianById(id: Int) = repository.getAktaKematianById(id)

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

    suspend fun getKIAById(id: Int) = repository.getKIAById(id)

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

    suspend fun getKKById(id: Int) = repository.getKKById(id)

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

    suspend fun getKTPById(id: Int) = repository.getKTPById(id)
    
    fun insertPengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData: PengajuanPelayananPemanfaatanData) {
        viewModelScope.launch { 
            repository.insertPengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData)
        }
    }
    
    fun deletePelayananPemanfaatanData(pengajuanPelayananPemanfaatanData: PengajuanPelayananPemanfaatanData) {
        viewModelScope.launch { 
            repository.deletePengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData)
        }
    }

    suspend fun getPelayananPemanfaatanDataById(id: Int) = repository.getPelayananPemanfaatanDataById(id)

    fun insertPengajuanSuratPindah(pengajuanSuratPindah: PengajuanSuratPindah) {
        viewModelScope.launch {
            repository.insertPengajuanSuratPindah(pengajuanSuratPindah)
        }
    }

    fun deletePengajuanSuratPindah(pengajuanSuratPindah : PengajuanSuratPindah) {
        viewModelScope.launch {
            repository.deletePengajuanSuratPindah(pengajuanSuratPindah)
        }
    }

    suspend fun getSuratPindahById(id: Int) = repository.getSuratPindahById(id)

}

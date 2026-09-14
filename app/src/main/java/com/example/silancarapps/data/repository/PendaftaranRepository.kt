package com.example.silancarapps.data.repository

import com.example.silancarapps.data.local.dao.*
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.data.local.PengajuanKIA
import com.example.silancarapps.data.local.PengajuanPelayananPemanfaatanData
import com.example.silancarapps.data.local.PengajuanSuratPindah
import com.example.silancarapps.data.local.User
import kotlinx.coroutines.flow.Flow

class PendaftaranRepository(
    private val aktaKelahiranDao: PengajuanAktaKelahiranDao,
    private val aktaKematianDao: PengajuanAktaKematianDao,
    private val kiaDao: PengajuanKIADao,
    private val kkDao: PengajuanKKDao,
    private val ktpDao: PengajuanKTPDao,
    private val userDao: UserDao,
    private val pengajuanPelayananPemanfaatanDao: PengajuanPelayananPemanfaatanDao,
    private val suratPindahDao: PengajuanSuratPindahDao
) {

    // Akta Kelahiran Operations
    fun getAllAktaKelahiran(): Flow<List<PengajuanAktaKelahiran>> = aktaKelahiranDao.getAllPengajuanAktaKelahiran()
    suspend fun insertAktaKelahiran(pengajuanAktaKelahiran: PengajuanAktaKelahiran) = aktaKelahiranDao.insertPengajuanAktaKelahiran(pengajuanAktaKelahiran)
    suspend fun deleteAktaKelahiran(pengajuanAktaKelahiran : PengajuanAktaKelahiran) = aktaKelahiranDao.deletePengajuanAktaKelahiran(pengajuanAktaKelahiran)
    suspend fun getAktaKelahiranById(id: Int) = aktaKelahiranDao.getPengajuanAktaKelahiranById(id)

    // Akta Kematian Operations
    fun getAllAktaKematian(): Flow<List<PengajuanAktaKematian>> = aktaKematianDao.getAllPengajuanAktaKematian()
    suspend fun insertAktaKematian(pengajuanAktaKematian: PengajuanAktaKematian) = aktaKematianDao.insertPengajuanAktaKematina(pengajuanAktaKematian)
    suspend fun deleteAktaKematian(pengajuanAktaKematian : PengajuanAktaKematian) = aktaKematianDao.deletePengajuanAktaKematian(pengajuanAktaKematian)
    suspend fun getAktaKematianById(id: Int) = aktaKematianDao.getPengajuanAktaKematianById(id)

    // KIA Operations
    fun getAllKIA(): Flow<List<PengajuanKIA>> = kiaDao.getAllPengajuanKIA()
    suspend fun insertKIA(pengajuanKIA: PengajuanKIA) = kiaDao.insertPengajuanKIA(pengajuanKIA)
    suspend fun deleteKIA(pengajuanKIA: PengajuanKIA) = kiaDao.deletePengajuanKIA(pengajuanKIA)
    suspend fun getKIAById(id: Int) = kiaDao.getPengajuanKIAById(id)

    // KK Operations
    fun getAllPengajuanKK(): Flow<List<PengajuanKK>> = kkDao.getAllPengajuanKK()
    suspend fun insertKK(pengajuan: PengajuanKK) = kkDao.insertPengajuanKK(pengajuan)
    suspend fun deleteKK(pengajuan: PengajuanKK) = kkDao.deletePengajuanKK(pengajuan)
    suspend fun getKKById(id: Int) = kkDao.getPengajuanKKById(id)

    // KTP Operations
    fun getAllPengajuanKTP(): Flow<List<PengajuanKTP>> = ktpDao.getAllPengajuanKTP()
    suspend fun insertKTP(pengajuanInsertKTP: PengajuanKTP) = ktpDao.insertPengajuanKTP(pengajuanInsertKTP)
    suspend fun deleteKTP(pengajuanKTP: PengajuanKTP) = ktpDao.deletePengajuanKTP(pengajuanKTP)
    suspend fun getKTPById(id: Int) = ktpDao.getPengajuanKTPById(id)

    // PelayananPemanfaatanData Operations
    fun getAllPengajuanPelayananPemanfaatanData(): Flow<List<PengajuanPelayananPemanfaatanData>> = pengajuanPelayananPemanfaatanDao.getAllPengajuanPelayananPemanfaatanData()
    suspend fun insertPengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData: PengajuanPelayananPemanfaatanData) = pengajuanPelayananPemanfaatanDao.insertPengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData)
    suspend fun deletePengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData: PengajuanPelayananPemanfaatanData) = pengajuanPelayananPemanfaatanDao.deletePengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData)
    suspend fun getPelayananPemanfaatanDataById(id: Int) = pengajuanPelayananPemanfaatanDao.getPengajuanPelayananPemanfaatanDataById(id)

    // Surat Pindah Operations
    fun getAllSuratPindah(): Flow<List<PengajuanSuratPindah>> = suratPindahDao.getAllPengajuanSuratPindah()
    suspend fun insertPengajuanSuratPindah(pengajuanSuratPindah: PengajuanSuratPindah) = suratPindahDao.insertPengajuanSuratPindah(pengajuanSuratPindah)
    suspend fun deletePengajuanSuratPindah(pengajuanSuratPindah: PengajuanSuratPindah) = suratPindahDao.deletePengajuanSuratPindah(pengajuanSuratPindah)
    suspend fun getSuratPindahById(id: Int) = suratPindahDao.getPengajuanSuratPindahById(id)

    // Auth Operations
    suspend fun registerUser(user: User) = userDao.registerUser(user)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
}

package com.example.silancarapps.data.repository

import com.example.silancarapps.data.local.dao.*
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.data.local.PengajuanKIA
import com.example.silancarapps.data.local.User
import kotlinx.coroutines.flow.Flow

class PendaftaranRepository(
    private val aktaKelahiranDao: PengajuanAktaKelahiranDao,
    private val aktaKematianDao: PengajuanAktaKematianDao,
    private val kiaDao: PengajuanKIADao,
    private val kkDao: PengajuanKKDao,
    private val ktpDao: PengajuanKTPDao,
    private val userDao: UserDao
) {

    // Akta Kelahiran Operations
    fun getAllAktaKelahiran(): Flow<List<PengajuanAktaKelahiran>> = aktaKelahiranDao.getAllPengajuanAktaKelahiran()
    suspend fun insertAktaKelahiran(pengajuanAktaKelahiran: PengajuanAktaKelahiran) = aktaKelahiranDao.insertPengajuanAktaKelahiran(pengajuanAktaKelahiran)
    suspend fun deleteAktaKelahiran(pengajuanAktaKelahiran : PengajuanAktaKelahiran) = aktaKelahiranDao.deletePengajuanAktaKelahiran(pengajuanAktaKelahiran)

    // Akta Kematian Operations
    fun getAllAktaKematian(): Flow<List<PengajuanAktaKematian>> = aktaKematianDao.getAllPengajuanAktaKematian()
    suspend fun insertAktaKematian(pengajuanAktaKematian: PengajuanAktaKematian) = aktaKematianDao.insertPengajuanAktaKematina(pengajuanAktaKematian)
    suspend fun deleteAktaKematian(pengajuanAktaKematian : PengajuanAktaKematian) = aktaKematianDao.deletePengajuanAktaKematian(pengajuanAktaKematian)

    // KIA Operations
    fun getAllKIA(): Flow<List<PengajuanKIA>> = kiaDao.getAllPengajuanKIA()
    suspend fun insertKIA(pengajuanKIA: PengajuanKIA) = kiaDao.insertPengajuanKIA(pengajuanKIA)
    suspend fun deleteKIA(pengajuanKIA: PengajuanKIA) = kiaDao.deletePengajuanKIA(pengajuanKIA)

    // KK Operations
    fun getAllPengajuanKK(): Flow<List<PengajuanKK>> = kkDao.getAllPengajuanKK()
    suspend fun insertKK(pengajuan: PengajuanKK) = kkDao.insertPengajuanKK(pengajuan)
    suspend fun deleteKK(pengajuan: PengajuanKK) = kkDao.deletePengajuanKK(pengajuan)

    // KTP Operations
    fun getAllPengajuanKTP(): Flow<List<PengajuanKTP>> = ktpDao.getAllPengajuanKTP()
    suspend fun insertKTP(pengajuanInsertKTP: PengajuanKTP) = ktpDao.insertPengajuanKTP(pengajuanInsertKTP)
    suspend fun deleteKTP(pengajuanKTP: PengajuanKTP) = ktpDao.deletePengajuanKTP(pengajuanKTP)

    // Auth Operations
    suspend fun registerUser(user: User) = userDao.registerUser(user)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
}

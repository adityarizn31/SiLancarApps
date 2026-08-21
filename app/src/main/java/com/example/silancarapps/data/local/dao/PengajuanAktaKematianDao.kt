package com.example.silancarapps.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.silancarapps.data.local.PengajuanAktaKematian
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanAktaKematianDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuanAktaKematina(pengajuanAktaKematian: PengajuanAktaKematian)

    @Query("SELECT * FROM pengajuanAktaKematian ORDER BY tanggal DESC")
    fun getAllPengajuanAktaKematian(): Flow<List<PengajuanAktaKematian>>

    @Query("SELECT * FROM pengajuanAktaKematian WHERE id = :id")
    suspend fun getPengajuanAktaKematianById(id: Int): PengajuanAktaKematian

    @Delete
    suspend fun deletePengajuanAktaKematian(pengajuanAktaKematian: PengajuanAktaKematian)
}

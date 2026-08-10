package com.example.silancarapps.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanAktaKelahiranDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuanAktaKelahiran(PengajuanAktaKelahiran: PengajuanAktaKelahiran)

    @Query("SELECT * FROM pengajuanaktakelahiran ORDER BY tanggal DESC")
    fun getAllPengajuanAktaKelahiran(): Flow<List<PengajuanAktaKelahiran>>

    @Query("SELECT * FROM pengajuanAktaKelahiran WHERE id = :id")
    suspend fun getPengajuanAktaKelahiranById(id: Int): PengajuanAktaKelahiran?

    @Delete
    suspend fun deletePengajuanAktaKelahiran(PengajuanAktaKelahiran: PengajuanAktaKelahiran)
}
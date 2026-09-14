package com.example.silancarapps.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.silancarapps.data.local.PengajuanSuratPindah
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanSuratPindahDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuanSuratPindah(pengajuanSuratPindah: PengajuanSuratPindah)

    @Query("SELECT * FROM pengajuanSuratPindah ORDER BY tanggal DESC")
    fun getAllPengajuanSuratPindah(): Flow<List<PengajuanSuratPindah>>

    @Query("SELECT * FROM pengajuanSuratPindah WHERE id = :id")
    suspend fun getPengajuanSuratPindahById(id: Int): PengajuanSuratPindah?

    @Delete
    suspend fun deletePengajuanSuratPindah(pengajuanSuratPindah: PengajuanSuratPindah)
}

package com.example.silancarapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanKKDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuan(pengajuanKK: PengajuanKK)

    @Query("SELECT * FROM pengajuanKK ORDER BY tanggal DESC")
    fun getAllPengajuan(): Flow<List<PengajuanKK>>

    @Query("SELECT * FROM pengajuanKK WHERE id = :id")
    suspend fun getPengajuanById(id: Int): PengajuanKK?

    @Delete
    suspend fun deletePengajuan(pengajuanKK: PengajuanKK)
}

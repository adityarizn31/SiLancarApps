package com.example.silancarapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuan(pengajuan: Pengajuan)

    @Query("SELECT * FROM pengajuan ORDER BY tanggal DESC")
    fun getAllPengajuan(): Flow<List<Pengajuan>>

    @Query("SELECT * FROM pengajuan WHERE id = :id")
    suspend fun getPengajuanById(id: Int): Pengajuan?

    @androidx.room.Delete
    suspend fun deletePengajuan(pengajuan: Pengajuan)
}

package com.example.silancarapps.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanKTPDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuanKTP(pengajuanKTP: PengajuanKTP)

    @Query("SELECT * FROM pengajuanKTP ORDER BY tanggal DESC")
    fun getAllPengajuanKTP(): Flow<List<PengajuanKTP>>

    @Query("SELECT * FROM pengajuanKTP WHERE id = :id")
    suspend fun getPengajuanKTPById(id: Int): PengajuanKTP?

    @Delete
    suspend fun deletePengajuan(pengajuanKTP: PengajuanKTP)
}



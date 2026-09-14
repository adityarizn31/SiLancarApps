package com.example.silancarapps.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.silancarapps.data.local.PengajuanPelayananPemanfaatanData
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanPelayananPemanfaatanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData: PengajuanPelayananPemanfaatanData)

    @Query("SELECT * FROM pengajuanPelayananPemanfaatanData ORDER BY tanggal DESC")
    fun getAllPengajuanPelayananPemanfaatanData(): Flow<List<PengajuanPelayananPemanfaatanData>>

    @Query("SELECT * FROM pengajuanPelayananPemanfaatanData WHERE id = :id")
    suspend fun getPengajuanPelayananPemanfaatanDataById(id: Int): PengajuanPelayananPemanfaatanData?

    @Delete
    suspend fun deletePengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData: PengajuanPelayananPemanfaatanData)

}
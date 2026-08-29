package com.example.silancarapps.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.silancarapps.data.local.PengajuanKIA
import kotlinx.coroutines.flow.Flow

@Dao
interface PengajuanKIADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengajuanKIA(PengajuanKIA: PengajuanKIA)

    @Query("SELECT * FROM pengajuankia ORDER BY tanggal DESC")
    fun getAllPengajuanKIA(): Flow<List<PengajuanKIA>>

    @Query("SELECT * FROM pengajuankia WHERE id = :id")
    suspend fun getPengajuanKIAById(id : Int): PengajuanKIA?

    @Delete
    suspend fun deletePengajuanKIA(PengajuanKIA: PengajuanKIA)
}
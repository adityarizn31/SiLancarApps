package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanaktakelahiran")
data class PengajuanAktaKelahiran (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val nama : String,
    val nik : String,
    val noKK: String,
    val noKTPSuami: String,
    val noKTPIstri: String,
    val bukuNikah: String,
    val alamat : String,
    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)
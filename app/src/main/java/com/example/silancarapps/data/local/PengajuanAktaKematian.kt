package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanAktaKematian")
data class PengajuanAktaKematian (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val jenisLayanan : String,
    val nama : String,
    val nik : String,
    val noKK: String,
    val noKTP: String,
    val noKTPSaksi: String,
    val noAkta: String,
    val alamat : String,
    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)
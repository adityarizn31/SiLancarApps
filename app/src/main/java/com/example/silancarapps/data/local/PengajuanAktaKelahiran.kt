package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanaktakelahiran")
data class PengajuanAktaKelahiran (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val jenisLayanan : String,
    val namaAnak : String,
    val jenisKelamin : String,
    val tempatLahir : String,
    val tanggalLahir : String,
    val waktuLahir : String,
    val anakKe : String,
    val beratBayi : String,
    val panjangBayi : String,
    
    val nikAyah : String,
    val namaAyah : String,
    val nikIbu : String,
    val namaIbu : String,

    val docSuratLahir : String?,
    val docKK : String?,
    val docKTPAyah : String?,
    val docKTPIbu : String?,

    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)

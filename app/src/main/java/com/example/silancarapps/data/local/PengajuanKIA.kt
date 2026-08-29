package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuankia")
data class PengajuanKIA(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val jenisLayanan : String,
    val namaLengkapAnak: String,
    val nikAnak: String,
    val tempatLahirAnak: String,
    val tanggalLahirAnak: String,
    val jenisKelaminAnak: String,
    val namaAyah: String,
    val nikAyah: String,
    val namaIbu: String,
    val nikIbu: String,

    val docAktaKelahiran : String?,
    val docKK : String,
    val docKTPAyah: String?,
    val docKTPIbu: String?,
    val docPasFotoAnak: String?,

    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)

package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanSuratPindah")
data class PengajuanSuratPindah (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val jenisLayanan: String,
    val nama : String,
    val nik : String,
    val noKK : String,
    val noHp : String,

    val alamatAsal : String,
    val desaAsal : String,
    val kecamatanAsal : String,

    val alamatTujuan : String,
    val desaTujuan : String,
    val kecamatanTujuan : String,

    val docSuratPindah : String?,
    val docKTP : String?,
    val docKK : String?,

    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)
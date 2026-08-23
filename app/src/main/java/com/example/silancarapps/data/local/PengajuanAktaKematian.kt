package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanAktaKematian")
data class PengajuanAktaKematian (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val jenisLayanan : String,

    val namaAlm : String,
    val nikAlm : String,
    val noKKAlm: String,
    val noAktaAlm: String,

    val nameSaksi : String,
    val nikSaksi: String,
    val noHpSaksi : String,
    val alamatSaksi : String,

    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)
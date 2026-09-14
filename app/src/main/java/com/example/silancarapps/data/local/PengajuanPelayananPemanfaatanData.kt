package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanPelayananPemanfaatanData")
data class PengajuanPelayananPemanfaatanData (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val jenisLayanan: String,
    val emailInstansi : String,
    val namaInstansi : String,
    val jenisInstansi : String,
    val alamatInstansi : String,
    val noHpInstansi : String,

    val namaPenanggungJawab : String,
    val jabatanPenanggungJawab : String,
    val nikPenanggungJawab : String,
    val noHpPenanggungJawab : String,

    val tujuanPemanfaatanData : String,
    val dataDibutuhkan : String,
    val keterangan : String,

    val docSuratPermohonanInstansi : String?,
    val docKTPPenanggungJawab : String?,
    val docSuratTugas : String?,
    val docLegalitasInstansi : String?,

    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)
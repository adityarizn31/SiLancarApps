package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuanKK")
data class PengajuanKK (
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val jenisLayanan: String,
    val nama : String,
    val nikSuami : String,
    val nikIstri : String,
    val noKKSuami : String,
    val noKKIstri : String,
    val noHp : String,
    val alamat : String,

    val docKTPSuami : String?,
    val docKTPIstri : String?,
    val docKKSuami : String?,
    val docKKIstri : String?,


    val status : String = "Menunggu",
    val tanggal : Long = System.currentTimeMillis()
)
package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuan")
data class Pengajuan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jenisLayanan: String,
    val nama: String,
    val nikSuami: String = "",
    val nikIstri: String = "",
    val noKKSuami: String = "",
    val noKKIstri: String = "",
    val nik: String = "", // Generic NIK for KTP etc
    val noKK: String = "", // Generic No KK for KTP etc
    val noHp: String,
    val alamat: String,
    val status: String = "Menunggu",
    val tanggal: Long = System.currentTimeMillis()
)

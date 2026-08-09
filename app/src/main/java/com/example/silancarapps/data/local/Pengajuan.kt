package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengajuan")
data class Pengajuan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jenisLayanan: String,
    val nama: String,
    val nik: String,
    val noKK: String,
    val noHp: String,
    val alamat: String,
    val status: String = "Menunggu",
    val tanggal: Long = System.currentTimeMillis()
)

package com.example.silancarapps.data.model

data class RiwayatSemua(
    val id: Int,
    val jenisLayanan: String,
    val nama: String,
    val status: String,
    val tanggal: Long,
    val dataAsli: Any // Untuk keperluan detail atau delete nanti
)

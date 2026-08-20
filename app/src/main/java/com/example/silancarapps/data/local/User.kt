package com.example.silancarapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val email: String,
    val nama: String,
    val password: String,
    val tanggal: Long = System.currentTimeMillis()
)

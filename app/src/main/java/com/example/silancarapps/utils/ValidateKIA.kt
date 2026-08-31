package com.example.silancarapps.utils

import com.example.silancarapps.R

object ValidateKIA {

    fun getNameAnakError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getNikAnakError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getTempatLahirError(tempat: String): Int? {
        return if (tempat.isEmpty()) R.string.err_empty_tempat_lahir else null
    }

    fun getTanggalLahirError(tanggal: String): Int? {
        return if (tanggal.isEmpty()) R.string.err_empty_tanggal_lahir else null
    }

    fun getNameOrangTuaError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getNikOrangTuaError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }
}

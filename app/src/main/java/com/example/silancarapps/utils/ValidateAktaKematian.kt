package com.example.silancarapps.utils

import com.example.silancarapps.R

object ValidateAktaKematian {

    fun getNameAlmError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getNikAlmError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getNoKKAlmError(noKK: String): Int? {
        return if (noKK.isEmpty()) R.string.err_empty_no_kk else null
    }

    fun getNameSaksiError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getNikSaksiError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getNoHpSaksiError(noHp: String): Int? {
        return if (noHp.length < 10 || noHp.length > 13 || !noHp.all { it.isDigit() }) R.string.err_invalid_no_hp else null
    }

    fun getAlamatSaksiError(alamat: String): Int? {
        return if (alamat.isEmpty()) R.string.err_empty_alamat else null
    }
}

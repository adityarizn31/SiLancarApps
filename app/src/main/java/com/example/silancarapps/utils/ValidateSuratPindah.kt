package com.example.silancarapps.utils

import com.example.silancarapps.R

object ValidateSuratPindah {
    fun getNameError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getNikError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getNoKKError(noKK: String): Int? {
        return if (noKK.isEmpty()) R.string.err_empty_no_kk else null
    }

    fun getNoHpError(noHp: String): Int? {
        return if (noHp.length < 10 || noHp.length > 13 || !noHp.all { it.isDigit() }) R.string.err_invalid_no_hp else null
    }

    fun getAlamatError(alamat: String): Int? {
        return if (alamat.isEmpty()) R.string.err_empty_alamat else null
    }

    fun getDesaError(desa: String): Int? {
        return if (desa.isEmpty()) R.string.err_empty_desa else null
    }

    fun getKecamatanError(kecamatan: String): Int? {
        return if (kecamatan.isEmpty()) R.string.err_empty_kecamatan else null
    }
}

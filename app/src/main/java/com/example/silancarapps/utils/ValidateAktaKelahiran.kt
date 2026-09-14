package com.example.silancarapps.utils

import com.example.silancarapps.R

object ValidateAktaKelahiran {

    fun getNameAnakError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getTempatLahirError(tempatLahir: String): Int? {
        return if (tempatLahir.isEmpty()) R.string.err_empty_tempat_lahir else null
    }

    fun getTanggalLahirError(tanggalLahir: String): Int? {
        return if (tanggalLahir.isEmpty()) R.string.err_empty_tanggal_lahir else null
    }

    fun getWaktuLahirError(waktuLahir: String): Int? {
        return if (waktuLahir.isEmpty()) R.string.err_empty_waktu_lahir else null
    }

    fun getAnakKeError(anakKe: String): Int? {
        return if (anakKe.isEmpty()) R.string.err_empty_anak_ke else null
    }

    fun getBeratBayiError(berat: String): Int? {
        return if (berat.isEmpty()) R.string.err_empty_berat_bayi else null
    }

    fun getPanjangBayiError(panjang: String): Int? {
        return if (panjang.isEmpty()) R.string.err_empty_panjang_bayi else null
    }

    fun getNikAyahError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getNamaAyahError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

    fun getNikIbuError(nik: String): Int? {
        return if (nik.length != 16 || !nik.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getNamaIbuError(name: String): Int? {
        return if (name.isEmpty()) R.string.err_empty_name else null
    }

}

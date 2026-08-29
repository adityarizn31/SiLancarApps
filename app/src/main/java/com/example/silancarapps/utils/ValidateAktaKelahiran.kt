package com.example.silancarapps.utils

object ValidateAktaKelahiran {

    fun isValidNameAnak(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isValidTempatLahir(tempatLahir: String): Boolean {
        return tempatLahir.isNotEmpty()
    }

    fun isValidTanggalLahir(tanggalLahir: String): Boolean {
        return tanggalLahir.isNotEmpty()
    }

    fun isValidWaktuLahir(waktuLahir: String): Boolean {
        return waktuLahir.isNotEmpty()
    }

    fun isValidAnakKe(anakKe: String): Boolean {
        return anakKe.isNotEmpty()
    }

    fun isValidBeratBayi(berat: String): Boolean {
        return berat.isNotEmpty()
    }

    fun isValidPanjangBayi(panjang: String): Boolean {
        return panjang.isNotEmpty()
    }

    fun isValidNik(nik: String): Boolean {
        return nik.length == 16 && nik.all { it.isDigit() }
    }

    fun isValidNameOrangTua(name: String): Boolean {
        return name.isNotEmpty()
    }
}

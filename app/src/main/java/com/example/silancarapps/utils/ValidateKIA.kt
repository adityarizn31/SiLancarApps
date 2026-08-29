package com.example.silancarapps.utils

object ValidateKIA {

    fun isValidNamaLengkapAnak(nameAnak : String): Boolean {
        return nameAnak.isNotEmpty()
    }

    fun isValidNikAnak(nikAnak : String): Boolean {
        return nikAnak.length == 16 && nikAnak.all { it.isDigit() }
    }

    fun isValidTempatLahirAnak(tempatLahirAnak : String): Boolean {
        return tempatLahirAnak.isNotEmpty()
    }

    fun isValidTanggalLahirAnak(tanggalLahirAnak : String): Boolean {
        return tanggalLahirAnak.isNotEmpty()
    }

    fun isValidJenisKelaminAnak(jenisKelaminAnak : String): Boolean {
        return jenisKelaminAnak.isNotEmpty()
    }

    fun isValidNamaAyah(namaAyah : String): Boolean {
        return namaAyah.isNotEmpty()
    }

    fun isValidNikAyah(nikAyah : String): Boolean {
        return nikAyah.length == 16 && nikAyah.all { it.isDigit() }
    }

    fun isValidNamaIbu(namaIbu : String): Boolean {
        return namaIbu.isNotEmpty()
    }

    fun isValidNikIbu(nikIbu : String): Boolean {
        return nikIbu.length == 16 && nikIbu.all { it.isDigit() }
    }
}
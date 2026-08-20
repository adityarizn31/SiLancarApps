package com.example.silancarapps.utils

object ValidateAktaKelahiran {

    fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isValidNik(nik: String): Boolean {
        return nik.isNotEmpty()
    }

    fun isValidNikSuami(nikSuami: String): Boolean {
        return nikSuami.length == 16 && nikSuami.all { it.isDigit() }
    }

    fun isValidNikIstri(nikIstri: String): Boolean {
        return nikIstri.length == 16 && nikIstri.all { it.isDigit() }
    }

    fun isValidNoKKSuami(noKKSuami: String): Boolean {
        return noKKSuami.isNotEmpty()
    }

    fun isValidNoKKIstri(noKKIstri: String): Boolean {
        return noKKIstri.isNotEmpty()
    }

    fun isValidNoHp(noHp: String): Boolean {
        return noHp.length == 12 && noHp.all { it.isDigit() }
    }

    fun isValidAlamat(alamat: String): Boolean {
        return alamat.isNotEmpty()
    }
}
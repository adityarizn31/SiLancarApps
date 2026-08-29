package com.example.silancarapps.utils

object ValidateKTP {

    fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isValidNik(nik: String): Boolean {
        return nik.length == 16 && nik.all { it.isDigit() }
    }

    fun isValidNoKK(noKK: String): Boolean {
        return noKK.isNotEmpty()
    }

    fun isValidNoHp(noHp: String): Boolean {
        return noHp.length == 12 && noHp.all { it.isDigit() }
    }

    fun isValidAlamat(alamat: String): Boolean {
        return alamat.isNotEmpty()
    }

}
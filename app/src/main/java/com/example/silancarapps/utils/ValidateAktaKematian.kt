package com.example.silancarapps.utils

object ValidateAktaKematian {

    fun isValidName(name : String): Boolean {
        return name.isNotEmpty()
    }

    fun isNikName(nik : String): Boolean {
        return nik.length == 16 && nik.all { it.isDigit() }
    }

    fun isNikAlmarhum(nikAlm : String): Boolean {
        return nikAlm.length == 16 && nikAlm.all { it.isDigit() }
    }

    fun isNoKKAlmarhum(noKKAlm : String): Boolean {
        return noKKAlm.isNotEmpty()
    }

    fun isNikSaksi(nikSaksi : String): Boolean {
        return nikSaksi.length == 16 && nikSaksi.all { it.isDigit() }
    }

    fun isValidNoHp(noHp: String): Boolean {
        return noHp.length == 12 && noHp.all { it.isDigit() }
    }

    fun isValidAlamat(alamat: String): Boolean {
        return alamat.isNotEmpty()
    }
}
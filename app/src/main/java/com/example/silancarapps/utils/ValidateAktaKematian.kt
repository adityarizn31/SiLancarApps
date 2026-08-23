package com.example.silancarapps.utils

object ValidateAktaKematian {

    fun isValidNameAlm(nameAlm : String): Boolean {
        return nameAlm.isNotEmpty()
    }

    fun isValidNikAlm(nikAlm : String): Boolean {
        return nikAlm.length == 16 && nikAlm.all { it.isDigit() }
    }

    fun isValidNoKKAlm(noKKAlm : String): Boolean {
        return noKKAlm.isNotEmpty()
    }

    fun isValidNoAktaLahirAlm(noAktaLahirAlm : String): Boolean {
        return noAktaLahirAlm.isNotEmpty()
    }

    fun isValidNameSaksi(nameSaksi : String): Boolean {
        return nameSaksi.isNotEmpty()
    }

    fun isValidNikSaksi(nikSaksi : String): Boolean {
        return nikSaksi.length == 16 && nikSaksi.all { it.isDigit() }
    }

    fun isValidNoHpSaksi(noHpSaksi: String): Boolean {
        return noHpSaksi.length == 12 && noHpSaksi.all { it.isDigit() }
    }

    fun isValidAlamatSaksi(alamatSaksi: String): Boolean {
        return alamatSaksi.isNotEmpty()
    }
}
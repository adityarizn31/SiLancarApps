package com.example.silancarapps.utils

object ValidateKK {

    fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isValidNik(nik: String): Boolean {
        return nik.length == 16 && nik.all { it.isDigit() }
    }

    fun isValidNoKKLama(noKKLama: String): Boolean {
        return noKKLama.isNotEmpty()
    }

    fun isValidNoHp(noHp: String): Boolean {
        return noHp.length == 12 && noHp.all { it.isDigit() }
    }

    fun isValidAlamat(alamat: String): Boolean {
        return alamat.isNotEmpty()
    }

    fun isValidrt(rt: String): Boolean {
        return rt.isNotEmpty()
    }

    fun isValidrw(rw: String): Boolean {
        return rw.isNotEmpty()
    }

    fun isValidKeluarahanan(kelurahan: String): Boolean {
        return kelurahan.isNotEmpty()
    }

    fun isValidKecamatan(kecamatan: String): Boolean {
        return kecamatan.isNotEmpty()
    }

    fun isValidKabupaten(kabupaten: String): Boolean {
        return kabupaten.isNotEmpty()
    }

    fun isValidProvinsi(provinsi: String): Boolean {
        return provinsi.isNotEmpty()
    }

    fun isValidKodePos(kodePos: String): Boolean {
        return kodePos.isNotEmpty()
    }

    fun isValidKtpPath(ktpPath: String): Boolean {
        return ktpPath.isNotEmpty()
    }

    fun isValidKkPath(kkPath: String): Boolean {
        return kkPath.isNotEmpty()
    }

    fun isValidDokumenPath(dokumenPath: String): Boolean {
        return dokumenPath.isNotEmpty()
    }
}
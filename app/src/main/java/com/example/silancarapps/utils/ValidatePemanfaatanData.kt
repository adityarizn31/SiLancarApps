package com.example.silancarapps.utils

import com.example.silancarapps.R

object ValidatePemanfaatanData {

    fun getNamaInstansiError(namaInstansi: String): Int? {
        return if (namaInstansi.isEmpty()) R.string.err_empty_nama_instansi else null
    }

    fun getEmailInstansiError(emailInstansi: String): Int? {
        return if (emailInstansi.isEmpty()) R.string.err_empty_email_instansi else null
    }

    fun getJenisIntansiError(jenisIntansi: String): Int ? {
        return if (jenisIntansi.isEmpty()) R.string.err_empty_jenis_instansi else null
    }

    fun getAlamatInstansiError(alamatInstansi: String): Int? {
        return if (alamatInstansi.isEmpty()) R.string.err_empty_alamat_instansi else null
    }

    fun getTeleponInstansiError(teleponInstansi: String): Int? {
        return if (teleponInstansi.length < 10 || teleponInstansi.length > 13 || !teleponInstansi.all { it.isDigit() }) R.string.err_invalid_no_hp else null
    }

    fun getNamaPenanggungJawabError(namaPenanggugng: String): Int ? {
        return if (namaPenanggugng.isEmpty()) R.string.err_empty_nama_penanggung_jawab else null
    }

    fun getJabatanPenanggungJawabError(jabatanPenanggungJawab: String): Int? {
        return if (jabatanPenanggungJawab.isEmpty()) R.string.err_empty_jabatan_penanggung_jawab else null
    }

    fun getNikPenanggungJawabError(nikPenanggungJawab: String): Int? {
        return if (nikPenanggungJawab.length != 16 || !nikPenanggungJawab.all { it.isDigit() }) R.string.err_invalid_nik else null
    }

    fun getNoHpPenanggungJawabError(noHpPenanggungJawab: String): Int? {
        return if (noHpPenanggungJawab.length < 10 || noHpPenanggungJawab.length > 13 || !noHpPenanggungJawab.all { it.isDigit() }) R.string.err_invalid_no_hp else null
    }

    fun getTujuanPermohonanError(tujuan: String): Int? {
        return if (tujuan.isEmpty()) R.string.err_empty_tujuan_pemanfaatan else null
    }

    fun getDataDibutuhkanError(dataDibutuhkan: String): Int? {
        return if (dataDibutuhkan.isEmpty()) R.string.err_empty_data_dibutuhkan else null
    }

    fun getKeteranganError(keterangan: String): Int? {
        return if (keterangan.isEmpty()) R.string.err_empty_keterangan else null
    }
}
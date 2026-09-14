package com.example.silancarapps.ui.pelayanan

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.R
import com.example.silancarapps.data.local.PengajuanPelayananPemanfaatanData
import com.example.silancarapps.databinding.FragmentPendaftaranPelayananPemanfaatanDataBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import com.example.silancarapps.utils.ValidatePemanfaatanData
import com.google.android.material.button.MaterialButton


class PendaftaranPelayananPemanfaatanDataFragment : Fragment() {

    private var _binding : FragmentPendaftaranPelayananPemanfaatanDataBinding ?= null
    private val binding get() = _binding!!

    private val viewModel : PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var suratPermohonanInstansi: android.net.Uri? = null

    private var ktpPJ: android.net.Uri? = null

    private var suratTugas: android.net.Uri? = null

    private var suratLegalitasIntansi: android.net.Uri? = null

    private val launcherIntentSuratPermohonanInstansi = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
         suratPermohonanInstansi = uri
         binding.tvSuratPelayanan.text = "File Terpilih"
        }
    }

    private val launcherKTPPJ = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            ktpPJ = uri
            binding.tvKTP.text = "File Terpilih"
        }
    }

    private val launcherSuratTugas = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        uri ->
        if (uri != null) {
            suratTugas = uri
            binding.tvSuratTugas.text = "File Terpilih"
        }
    }

    private val launcherSuratLegalitasIntansi = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        uri ->
        if (uri != null) {
            suratLegalitasIntansi = uri
            binding.tvLegalitas.text = "File Terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranPelayananPemanfaatanDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUploadButtons()

        binding.btnKirim.setOnClickListener {
            validateAndProcess()
        }

    }

    private fun setupUploadButtons() {
        binding.btnUploadFormSuratPermohonan.setOnClickListener { launcherIntentSuratPermohonanInstansi.launch("image/*") }
        binding.btnUploadKTP.setOnClickListener { launcherKTPPJ.launch("image/*") }
        binding.btnUploadSuratTugas.setOnClickListener { launcherSuratTugas.launch("image/*") }
        binding.btnUploadLegalitas.setOnClickListener { launcherSuratLegalitasIntansi.launch("image/*") }
    }

    private fun validateAndProcess() {
        val namaInstansi = binding.edtNamaInstansi.text.toString().trim()
        val emailInstansi = binding.edtEmailInstansi.text.toString().trim()
        val jenisInstansi = binding.edtJenisInstansi.text.toString().trim()
        val alamatInstansi = binding.edtAlamatInstansi.text.toString().trim()
        val teleponInstansi = binding.edtTeleponInstansi.text.toString().trim()

        val penanggungJawab = binding.edtNamaPenanggungJawab.text.toString().trim()
        val jabatanPJ = binding.edtJabatan.text.toString().trim()
        val nikPJ = binding.edtNikPenanggungJawab.text.toString().trim()
        val noHPPJ = binding.edtNoHp.text.toString().trim()

        val tujuanPermohonan = binding.edtTujuanPemanfaatan.text.toString().trim()
        val dataPermohonan = binding.edtDataDibutuhkan.text.toString().trim()
        val keterangan = binding.edtKeterangan.text.toString().trim()

        // 1. Memvalidasi teks
        val namaInstansiErr = ValidatePemanfaatanData.getNamaInstansiError(namaInstansi)
        val emailInstansiErr = ValidatePemanfaatanData.getEmailInstansiError(emailInstansi)
        val jenisInstansiErr = ValidatePemanfaatanData.getJenisIntansiError(jenisInstansi)
        val alamatInstansiErr = ValidatePemanfaatanData.getAlamatInstansiError(alamatInstansi)
        val teleponInstansiErr = ValidatePemanfaatanData.getTeleponInstansiError(teleponInstansi)

        val penanggungJawabErr = ValidatePemanfaatanData.getNamaPenanggungJawabError(penanggungJawab)
        val jabatanPJErr = ValidatePemanfaatanData.getJabatanPenanggungJawabError(jabatanPJ)
        val nikPJErr = ValidatePemanfaatanData.getNikPenanggungJawabError(nikPJ)
        val noHpPJErr = ValidatePemanfaatanData.getNoHpPenanggungJawabError(noHPPJ)

        val tujuanPermohonanErr = ValidatePemanfaatanData.getTujuanPermohonanError(tujuanPermohonan)
        val dataPermohonanErr = ValidatePemanfaatanData.getDataDibutuhkanError(dataPermohonan)
        val keteranganErr = ValidatePemanfaatanData.getKeteranganError(keterangan)


        // 2. Menampilkan error di UI
        binding.edtNamaInstansi.error = namaInstansiErr?.let { getString(it) }
        binding.edtEmailInstansi.error = emailInstansiErr?.let { getString(it) }
        binding.edtJenisInstansi.error = jenisInstansiErr?.let { getString(it) }
        binding.edtAlamatInstansi.error = alamatInstansiErr?.let { getString(it) }
        binding.edtTeleponInstansi.error = teleponInstansiErr?.let { getString(it) }

        binding.edtNamaPenanggungJawab.error = penanggungJawabErr?.let { getString(it) }
        binding.edtJabatan.error = jabatanPJErr?.let { getString(it) }
        binding.edtNikPenanggungJawab.error = nikPJErr?.let { getString(it) }
        binding.edtNoHp.error = noHpPJErr?.let { getString(it) }

        binding.edtTujuanPemanfaatan.error = tujuanPermohonanErr?.let { getString(it) }
        binding.edtDataDibutuhkan.error = dataPermohonanErr?.let { getString(it) }
        binding.edtKeterangan.error = keteranganErr?.let { getString(it) }

        // 3. Mengecek apakah ada error
        val hasError = listOf( namaInstansiErr, emailInstansiErr, jenisInstansiErr, alamatInstansiErr, teleponInstansiErr, penanggungJawabErr, jabatanPJErr,nikPJErr, noHpPJErr, tujuanPermohonanErr, dataPermohonanErr, keteranganErr ).any { it != null }

        if (hasError) {
            Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Mengecek Persetujuan
        if (!binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), getString(R.string.harap_lampirkan_dokumen), Toast.LENGTH_SHORT).show()
            return
        }

        // 5. Mengecek lampiran
        if (suratPermohonanInstansi == null || ktpPJ == null || suratTugas == null || suratLegalitasIntansi == null) {
            Toast.makeText(requireContext(), getString(R.string.harap_lampirkan_dokumen), Toast.LENGTH_SHORT).show()
            return
        }

        // 6.
        showConfirmationDialog(
            namaInstansi,
            emailInstansi,
            jenisInstansi,
            alamatInstansi,
            teleponInstansi,
            penanggungJawab,
            jabatanPJ,
            nikPJ,
            noHPPJ,
            tujuanPermohonan,
            dataPermohonan,
            keterangan
        )

    }

    private fun showConfirmationDialog(
        namaInstansi: String,
        emailInstansi: String,
        jenisInstansi: String,
        alamatInstansi: String,
        teleponInstansi: String,
        penanggungJawab: String,
        jabatanPJ: String,
        nikPJ: String,
        noHPPJ: String,
        tujuanPermohonan: String,
        dataPermohonan: String,
        keterangan: String
    ) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<MaterialButton>(R.id.btnKirim)

        val summary = """
            Nama Instansi: $namaInstansi
            Email Instansi: $emailInstansi
            Jenis Instansi: $jenisInstansi
            Alamat Instansi: $alamatInstansi
            Telepon Instansi: $teleponInstansi
            Nama Penanggung Jawab: $penanggungJawab
            Jabatan Penanggung Jawab: $jabatanPJ
            NIK Penanggung Jawab: $nikPJ
            No HP Penanggung Jawab: $noHPPJ
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener { dialog.dismiss() }
        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData(namaInstansi, emailInstansi, jenisInstansi, alamatInstansi, teleponInstansi, penanggungJawab, jabatanPJ, nikPJ, noHPPJ, tujuanPermohonan, dataPermohonan, keterangan)
        }
    }

    private fun submitData(
        namaInstansi: String,
        emailInstansi: String,
        jenisInstansi: String,
        alamatInstansi: String,
        teleponInstansi: String,

        penanggungJawab: String,
        jabatanPJ: String,
        nikPJ: String,
        noHPPJ: String,

        tujuanPermohonan: String,
        dataPermohonan: String,
        keterangan: String
    ) {
        val fileSuratPermohonan = FileUtils.uriToFile(suratPermohonanInstansi!!, requireContext())
        val fileKTP = FileUtils.uriToFile(ktpPJ!!, requireContext())
        val fileSuratTugas = FileUtils.uriToFile(suratTugas!!, requireContext())
        val fileSuratLegalitas = FileUtils.uriToFile(suratLegalitasIntansi!!, requireContext())

        val pengajuanPelayananPemanfaatanData = PengajuanPelayananPemanfaatanData(
            jenisLayanan = "Pelayanan Pemanfaatan Data",
            namaInstansi = namaInstansi,
            emailInstansi = emailInstansi,
            jenisInstansi = jenisInstansi,
            alamatInstansi = alamatInstansi,
            noHpInstansi = teleponInstansi,
            
            namaPenanggungJawab = penanggungJawab,
            jabatanPenanggungJawab = jabatanPJ,
            nikPenanggungJawab = nikPJ,
            noHpPenanggungJawab = noHPPJ,
            
            tujuanPemanfaatanData = tujuanPermohonan,
            dataDibutuhkan = dataPermohonan,
            keterangan = keterangan,
            
            docSuratPermohonanInstansi = fileSuratPermohonan.absolutePath,
            docKTPPenanggungJawab = fileKTP.absolutePath,
            docSuratTugas = fileSuratTugas.absolutePath,
            docLegalitasInstansi = fileSuratLegalitas.absolutePath
        )

        viewModel.insertPengajuanPelayananPemanfaatanData(pengajuanPelayananPemanfaatanData)
        Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
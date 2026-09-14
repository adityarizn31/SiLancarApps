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
import com.example.silancarapps.data.local.PengajuanSuratPindah
import com.example.silancarapps.databinding.FragmentPendaftaranSuratPindahBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import com.example.silancarapps.utils.ValidateSuratPindah
import com.google.android.material.button.MaterialButton

class PendaftaranSuratPindahFragment : Fragment() {

    private var _binding : FragmentPendaftaranSuratPindahBinding? = null
    private val binding  get() = _binding!!
    private val viewModel : PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var fileSuratPindah: android.net.Uri? = null
    private var fileKtpPemohon: android.net.Uri? = null
    private var fileKKPemohon: android.net.Uri? = null

    private val launcherIntentSuratPindah = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            fileSuratPindah = uri
            binding.tvSuratPindah.text = "File terpilih"
        }
    }

    private val launcherIntentKtpPemohon = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            fileKtpPemohon = uri
            binding.tvKTP.text = "File Terpilih"
        }
    }

    private val launcherIntentKKPemohon = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            fileKKPemohon = uri
            binding.tvKK.text = "File Terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranSuratPindahBinding.inflate(inflater, container, false)
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
        binding.btnUploadFormSuratPindah.setOnClickListener { launcherIntentSuratPindah.launch("image/*") }
        binding.btnUploadKTP.setOnClickListener { launcherIntentKtpPemohon.launch("image/*") }
        binding.btnUploadKK.setOnClickListener { launcherIntentKKPemohon.launch("image/*") }
    }

    private fun validateAndProcess() {
        // Data Pemohon
        val nama = binding.edtNama.text.toString().trim()
        val nik = binding.edtNik.text.toString().trim()
        val noKK = binding.edtNoKK.text.toString().trim()
        val noHp = binding.edtNoHp.text.toString().trim()

        // Alamat Asal
        val alamatAsal = binding.edtAlamatAsal.text.toString().trim()
        val desaAsal = binding.edtDesaAsal.text.toString().trim()
        val kecamatanAsal = binding.edtKecamatanAsal.text.toString().trim()

        // Alamat Tujuan
        val alamatTujuan = binding.edtAlamatTujuan.text.toString().trim()
        val desaTujuan = binding.edtDesaTujuan.text.toString().trim()
        val kecamatanTujuan = binding.edtKecamatanTujuan.text.toString().trim()

        // 1. Memvalidasti teks
        val nameErr = ValidateSuratPindah.getNameError(nama)
        val nikErr = ValidateSuratPindah.getNikError(nik)
        val noKKErr = ValidateSuratPindah.getNoKKError(noKK)
        val noHpErr = ValidateSuratPindah.getNoHpError(noHp)

        val alamatAsalErr = ValidateSuratPindah.getAlamatError(alamatAsal)
        val desaAsalErr = ValidateSuratPindah.getDesaError(desaAsal)
        val kecamatanAsalErr = ValidateSuratPindah.getKecamatanError(kecamatanAsal)

        val alamatTujuanErr = ValidateSuratPindah.getAlamatError(alamatTujuan)
        val desaTujuanErr = ValidateSuratPindah.getDesaError(desaTujuan)
        val kecamatanTujuanErr = ValidateSuratPindah.getKecamatanError(kecamatanTujuan)

        // 2. Menampilkan error di UI
        binding.edtNama.error = nameErr?.let { getString(it) }
        binding.edtNik.error = nikErr?.let { getString(it) }
        binding.edtNoKK.error = noKKErr?.let { getString(it) }
        binding.edtNoHp.error = noHpErr?.let { getString(it) }

        binding.edtAlamatAsal.error = alamatAsalErr?.let { getString(it) }
        binding.edtDesaAsal.error = desaAsalErr?.let { getString(it) }
        binding.edtKecamatanAsal.error = kecamatanAsalErr?.let { getString(it) }

        binding.edtAlamatTujuan.error = alamatTujuanErr?.let { getString(it) }
        binding.edtDesaTujuan.error = desaTujuanErr?.let { getString(it) }
        binding.edtKecamatanTujuan.error = kecamatanTujuanErr?.let { getString(it) }

        // 3. Mengecek apakah ada error
        val hasError = listOf(
            nameErr, nikErr, noKKErr, noHpErr,
            alamatAsalErr, desaAsalErr, kecamatanAsalErr,
            alamatTujuanErr, desaTujuanErr, kecamatanTujuanErr
        ).any { it != null }

        if (hasError) {
            Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Mengecek persetujuan
        if (!binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), getString(R.string.err_empty_persetujuan), Toast.LENGTH_SHORT).show()
            return
        }

        // 5. Mengecek lampiran
        if (fileSuratPindah == null || fileKtpPemohon == null || fileKKPemohon == null) {
            Toast.makeText(requireContext(), getString(R.string.harap_lampirkan_dokumen), Toast.LENGTH_SHORT).show()
            return
        }

        // 6. Menampilkan dialog konfirmasi
        showConfirmationDialog(
            nama, nik, noKK, noHp,
            alamatAsal, desaAsal, kecamatanAsal,
            alamatTujuan, desaTujuan, kecamatanTujuan
        )
    }

    private fun showConfirmationDialog(
        nama: String, nik: String, noKK: String, noHp: String,
        alamatAsal: String, desaAsal: String, kecamatanAsal: String,
        alamatTujuan: String, desaTujuan: String, kecamatanTujuan: String
    ) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<MaterialButton>(R.id.btnKirim)

        val summary = """
            Nama Lengkap: $nama
            NIK: $nik
            No KK: $noKK
            No HP: $noHp
            Alamat Asal: $alamatAsal, $desaAsal, $kecamatanAsal
            Alamat Tujuan: $alamatTujuan, $desaTujuan, $kecamatanTujuan
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener { dialog.dismiss() }
        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData(nama, nik, noKK, noHp, alamatAsal, desaAsal, kecamatanAsal, alamatTujuan, desaTujuan, kecamatanTujuan)
        }
    }

    private fun submitData(
        nama: String, nik: String, noKK: String, noHp: String,
        alamatAsal: String, desaAsal: String, kecamatanAsal: String,
        alamatTujuan: String, desaTujuan: String, kecamatanTujuan: String
    ) {
        val filePindah = FileUtils.uriToFile(fileSuratPindah!!, requireContext())
        val fileKtp = FileUtils.uriToFile(fileKtpPemohon!!, requireContext())
        val fileKk = FileUtils.uriToFile(fileKKPemohon!!, requireContext())

        val pengajuanSuratPindah = PengajuanSuratPindah(
            jenisLayanan = "Pendaftaran Surat Pindah",
            nama = nama,
            nik = nik,
            noKK = noKK,
            noHp = noHp,

            alamatAsal = alamatAsal,
            desaAsal = desaAsal,
            kecamatanAsal = kecamatanAsal,

            alamatTujuan = alamatTujuan,
            desaTujuan = desaTujuan,
            kecamatanTujuan = kecamatanTujuan,

            docSuratPindah = filePindah.absolutePath,
            docKTP = fileKtp.absolutePath,
            docKK = fileKk.absolutePath
        )

        viewModel.insertPengajuanSuratPindah(pengajuanSuratPindah)
        Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

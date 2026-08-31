package com.example.silancarapps.ui.pelayanan

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.R
import com.example.silancarapps.data.local.PengajuanKIA
import com.example.silancarapps.databinding.FragmentPendaftaranKartuIdentitasAnakBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import com.example.silancarapps.utils.ValidateKIA
import java.util.Calendar

class PendaftaranKartuIdentitasAnakFragment : Fragment() {

    private var _binding : FragmentPendaftaranKartuIdentitasAnakBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var aktaKelahiran : Uri? = null
    private var kk : Uri? = null
    private var ktpAyah : Uri? = null
    private var ktpIbu : Uri? = null
    private var pasFotoAnak : Uri? = null

    private val launcherIntentAktaKelahiran = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            aktaKelahiran = uri
            binding.tvAktaKelahiran.text = "File terpilih"
        }
    }

    private val launcherIntentKK = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            kk = uri
            binding.tvKK.text = "File terpilih"
        }
    }

    private val launcherIntentKTPAyah = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            ktpAyah = uri
            binding.tvKTPAyah.text = "File terpilih"
        }
    }

    private val launcherIntentKTPIBu = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            ktpIbu = uri
            binding.tvKTPIbu.text = "File terpilih"
        }
    }

    private val launcherIntentPasFotoAnak = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            pasFotoAnak = uri
            binding.tvPasFotoAnak.text = "File terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranKartuIdentitasAnakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPickers()
        setupUploadButtons()

        binding.btnKirim.setOnClickListener {
            validateAndProcess()
        }
    }

    private fun setupPickers() {
        binding.edtTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                binding.edtTanggalLahir.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupUploadButtons() {
        binding.btnUploadAktaKelahiran.setOnClickListener { launcherIntentAktaKelahiran.launch("image/*") }
        binding.btnUploadKK.setOnClickListener { launcherIntentKK.launch("image/*") }
        binding.btnUploadKtpAyah.setOnClickListener { launcherIntentKTPAyah.launch("image/*") }
        binding.btnUploadKtpIbu.setOnClickListener { launcherIntentKTPIBu.launch("image/*") }
        binding.btnUploadPasFoto.setOnClickListener { launcherIntentPasFotoAnak.launch("image/*") }
    }

    private fun validateAndProcess() {
        val namaLengkapAnak = binding.edtNamaAnak.text.toString().trim()
        val nikAnak = binding.edtNikAnak.text.toString().trim()
        val tempatLahirAnak = binding.edtTempatLahir.text.toString().trim()
        val tanggalLahirAnak = binding.edtTanggalLahir.text.toString().trim()
        val namaAyah = binding.edtNamaAyah.text.toString().trim()
        val namaIbu = binding.edtNamaIbu.text.toString().trim()
        val nikAyah = binding.edtNikAyah.text.toString().trim()
        val nikIbu = binding.edtNikIbu.text.toString().trim()

        val selectedGenderId = binding.rgJenisKelamin.checkedRadioButtonId
        val jenisKelamin = if (selectedGenderId == R.id.rbLaki) "Laki-laki" else "Perempuan"

        // 1. Validasi Teks
        val namaAnakErr = ValidateKIA.getNameAnakError(namaLengkapAnak)
        val nikAnakErr = ValidateKIA.getNikAnakError(nikAnak)
        val tempatErr = ValidateKIA.getTempatLahirError(tempatLahirAnak)
        val tglErr = ValidateKIA.getTanggalLahirError(tanggalLahirAnak)
        val namaAyahErr = ValidateKIA.getNameOrangTuaError(namaAyah)
        val nikAyahErr = ValidateKIA.getNikOrangTuaError(nikAyah)
        val namaIbuErr = ValidateKIA.getNameOrangTuaError(namaIbu)
        val nikIbuErr = ValidateKIA.getNikOrangTuaError(nikIbu)

        // 2. Set Error ke UI
        binding.edtNamaAnak.error = namaAnakErr?.let { getString(it) }
        binding.edtNikAnak.error = nikAnakErr?.let { getString(it) }
        binding.edtTempatLahir.error = tempatErr?.let { getString(it) }
        binding.edtTanggalLahir.error = tglErr?.let { getString(it) }
        binding.edtNamaAyah.error = namaAyahErr?.let { getString(it) }
        binding.edtNikAyah.error = nikAyahErr?.let { getString(it) }
        binding.edtNamaIbu.error = namaIbuErr?.let { getString(it) }
        binding.edtNikIbu.error = nikIbuErr?.let { getString(it) }

        val hasError = listOf(namaAnakErr, nikAnakErr, tempatErr, tglErr, namaAyahErr, nikAyahErr, namaIbuErr, nikIbuErr).any { it != null }

        if (hasError) {
            Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
            return
        }

        if (!binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), getString(R.string.err_empty_persetujuan), Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Cek Lampiran
        if (aktaKelahiran == null || kk == null || ktpAyah == null || ktpIbu == null || pasFotoAnak == null) {
            Toast.makeText(requireContext(), getString(R.string.harap_lampirkan_dokumen), Toast.LENGTH_SHORT).show()
            return
        }

        showConfirmationDialog(namaLengkapAnak, nikAnak, tempatLahirAnak, tanggalLahirAnak, jenisKelamin, namaAyah, nikAyah, namaIbu, nikIbu)
    }

    private fun showConfirmationDialog(
        nama: String, nik: String, tempat: String, tgl: String, jk: String,
        nAyah: String, nikAyah: String, nIbu: String, nikIbu: String
    ) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<android.widget.TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnKirim)

        val summary = """
            Nama Anak: $nama
            NIK Anak: $nik
            Tempat/Tgl Lahir: $tempat, $tgl
            Jenis Kelamin: $jk
            Ayah: $nAyah ($nikAyah)
            Ibu: $nIbu ($nikIbu)
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener { dialog.dismiss() }
        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData(nama, nik, tempat, tgl, jk, nAyah, nikAyah, nIbu, nikIbu)
        }
        dialog.show()
    }

    private fun submitData(
        nama: String, nik: String, tempat: String, tgl: String, jk: String,
        nAyah: String, nikAyah: String, nIbu: String, nikIbu: String
    ) {
        val fileAkta = FileUtils.uriToFile(aktaKelahiran!!, requireContext())
        val fileKK = FileUtils.uriToFile(kk!!, requireContext())
        val fileKtpAyah = FileUtils.uriToFile(ktpAyah!!, requireContext())
        val fileKtpIbu = FileUtils.uriToFile(ktpIbu!!, requireContext())
        val filePasFoto = FileUtils.uriToFile(pasFotoAnak!!, requireContext())

        val pengajuan = PengajuanKIA(
            jenisLayanan = "Pendaftaran KIA",
            namaLengkapAnak = nama,
            nikAnak = nik,
            tempatLahirAnak = tempat,
            tanggalLahirAnak = tgl,
            jenisKelaminAnak = jk,
            namaAyah = nAyah,
            nikAyah = nikAyah,
            namaIbu = nIbu,
            nikIbu = nikIbu,
            docAktaKelahiran = fileAkta.absolutePath,
            docKK = fileKK.absolutePath,
            docKTPAyah = fileKtpAyah.absolutePath,
            docKTPIbu = fileKtpIbu.absolutePath,
            docPasFotoAnak = filePasFoto.absolutePath
        )
        
        viewModel.insertKIA(pengajuan)
        Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

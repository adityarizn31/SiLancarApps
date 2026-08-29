package com.example.silancarapps.ui.pelayanan

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.R
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.databinding.FragmentPendaftaranAktaKelahiranBinding
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import java.util.Calendar

class PendaftaranAktaKelahiranFragment : Fragment() {

    private var _binding : FragmentPendaftaranAktaKelahiranBinding? = null
    private val binding get() = _binding!!
    private val viewModel : PengajuanViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var uriSuratLahir: Uri? = null
    private var uriKK: Uri? = null
    private var uriKtpAyah: Uri? = null
    private var uriKtpIbu: Uri? = null

    private val launcherSuratLahir = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriSuratLahir = uri
            binding.tvSuratKeteranganLahir.text = "File terpilih"
        }
    }

    private val launcherKK = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKK = uri
            binding.tvKK.text = "File terpilih"
        }
    }

    private val launcherKtpAyah = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKtpAyah = uri
            binding.tvKtpAyah.text = "File terpilih"
        }
    }

    private val launcherKtpIbu = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKtpIbu = uri
            binding.tvKtpIbu.text = "File terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranAktaKelahiranBinding.inflate(inflater, container, false)
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

        binding.edtWaktuLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(requireContext(), { _, hour, minute ->
                binding.edtWaktuLahir.setText(String.format("%02d:%02d", hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
    }

    private fun setupUploadButtons() {
        binding.btnUploadSuratKeteranganLahir.setOnClickListener { launcherSuratLahir.launch("image/*") }
        binding.btnUploadKK.setOnClickListener { launcherKK.launch("image/*") }
        binding.btnUploadKtpAyah.setOnClickListener { launcherKtpAyah.launch("image/*") }
        binding.btnUploadKtpIbu.setOnClickListener { launcherKtpIbu.launch("image/*") }
    }

    private fun validateAndProcess() {
        val namaAnak = binding.edtNamaAnak.text.toString().trim()
        val tempatLahir = binding.edtTempatLahir.text.toString().trim()
        val tanggalLahir = binding.edtTanggalLahir.text.toString().trim()
        val waktuLahir = binding.edtWaktuLahir.text.toString().trim()
        val anakKe = binding.edtAnakKe.text.toString().trim()
        val beratBayi = binding.edtBeratBayi.text.toString().trim()
        val panjangBayi = binding.edtPanjangBayi.text.toString().trim()
        val nikAyah = binding.edtNikAyah.text.toString().trim()
        val namaAyah = binding.edtNamaAyah.text.toString().trim()
        val nikIbu = binding.edtNikIbu.text.toString().trim()
        val namaIbu = binding.edtNamaIbu.text.toString().trim()

        val selectedGenderId = binding.rgJenisKelamin.checkedRadioButtonId
        val jenisKelamin = if (selectedGenderId == R.id.rbLaki) "Laki-laki" else "Perempuan"

        if (namaAnak.isEmpty() || tempatLahir.isEmpty() || tanggalLahir.isEmpty() || nikAyah.length != 16 || nikIbu.length != 16 || !binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), "Lengkapi semua data dan centang persetujuan", Toast.LENGTH_SHORT).show()
            return
        }

        if (uriSuratLahir == null || uriKK == null || uriKtpAyah == null || uriKtpIbu == null) {
            Toast.makeText(requireContext(), "Harap lampirkan semua dokumen persyaratan", Toast.LENGTH_SHORT).show()
            return
        }

        showConfirmationDialog(namaAnak, jenisKelamin, tempatLahir, tanggalLahir, waktuLahir, anakKe, beratBayi, panjangBayi, nikAyah, namaAyah, nikIbu, namaIbu)
    }

    private fun showConfirmationDialog(
        namaAnak: String, jenisKelamin: String, tempatLahir: String, tanggalLahir: String,
        waktuLahir: String, anakKe: String, beratBayi: String, panjangBayi: String,
        nikAyah: String, namaAyah: String, nikIbu: String, namaIbu: String
    ) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<android.widget.TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnKirim)

        val summary = """
            Nama Anak: $namaAnak
            Jenis Kelamin: $jenisKelamin
            Tempat/Tgl Lahir: $tempatLahir, $tanggalLahir
            Anak Ke: $anakKe
            Data Orang Tua:
            Ayah: $namaAyah ($nikAyah)
            Ibu: $namaIbu ($nikIbu)
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener { dialog.dismiss() }

        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData(namaAnak, jenisKelamin, tempatLahir, tanggalLahir, waktuLahir, anakKe, beratBayi, panjangBayi, nikAyah, namaAyah, nikIbu, namaIbu)
        }

        dialog.show()
    }

    private fun submitData(
        namaAnak: String, jenisKelamin: String, tempatLahir: String, tanggalLahir: String,
        waktuLahir: String, anakKe: String, beratBayi: String, panjangBayi: String,
        nikAyah: String, namaAyah: String, nikIbu: String, namaIbu: String
    ) {
        val fileSuratLahir = FileUtils.uriToFile(uriSuratLahir!!, requireContext())
        val fileKK = FileUtils.uriToFile(uriKK!!, requireContext())
        val fileKtpAyah = FileUtils.uriToFile(uriKtpAyah!!, requireContext())
        val fileKtpIbu = FileUtils.uriToFile(uriKtpIbu!!, requireContext())

        val pengajuan = PengajuanAktaKelahiran(
            jenisLayanan = "Pendaftaran Akta Kelahiran",
            namaAnak = namaAnak,
            jenisKelamin = jenisKelamin,
            tempatLahir = tempatLahir,
            tanggalLahir = tanggalLahir,
            waktuLahir = waktuLahir,
            anakKe = anakKe,
            beratBayi = beratBayi,
            panjangBayi = panjangBayi,
            nikAyah = nikAyah,
            namaAyah = namaAyah,
            nikIbu = nikIbu,
            namaIbu = namaIbu,
            docSuratLahir = fileSuratLahir.absolutePath,
            docKK = fileKK.absolutePath,
            docKTPAyah = fileKtpAyah.absolutePath,
            docKTPIbu = fileKtpIbu.absolutePath
        )

        viewModel.insertAktaKelahiran(pengajuan)
        Toast.makeText(requireContext(), "Pengajuan Akta Kelahiran berhasil dikirim!", Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

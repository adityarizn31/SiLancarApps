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
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.databinding.FragmentPendaftaranAktaKematianBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import java.util.Calendar

class PendaftaranAktaKematianFragment : Fragment() {

    private var _binding: FragmentPendaftaranAktaKematianBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var uriKtpAlm: Uri? = null
    private var uriKkAlm: Uri? = null
    private var uriAktaLahirAlm: Uri? = null
    private var uriKtpSaksi: Uri? = null

    private val launcherKtpAlm = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKtpAlm = uri
            binding.tvKtpAlmarhum.text = "File terpilih"
        }
    }

    private val launcherKkAlm = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKkAlm = uri
            binding.tvKKAlm.text = "File terpilih"
        }
    }

    private val launcherAktaLahirAlm = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriAktaLahirAlm = uri
            binding.tvAktaKelahiranAlm.text = "File terpilih"
        }
    }

    private val launcherKtpSaksi = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKtpSaksi = uri
            binding.tvKTPSaksi.text = "File terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranAktaKematianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatePicker()
        setupUploadButtons()

        binding.btnKirim.setOnClickListener {
            validateAndProcess()
        }
    }

    private fun setupDatePicker() {
        binding.edtTanggalKematian.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                binding.edtTanggalKematian.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupUploadButtons() {
        binding.btnUploadKTPAlm.setOnClickListener { launcherKtpAlm.launch("image/*") }
        binding.btnUploadKKAlm.setOnClickListener { launcherKkAlm.launch("image/*") }
        binding.btnUploadAktaKelahiranAlm.setOnClickListener { launcherAktaLahirAlm.launch("image/*") }
        binding.btnUploadKTPSaksi.setOnClickListener { launcherKtpSaksi.launch("image/*") }
    }

    private fun validateAndProcess() {
        val namaAlm = binding.edtNama.text.toString().trim()
        val nikAlm = binding.edtNikAlm.text.toString().trim()
        val noKKAlm = binding.edtNoKKAlm.text.toString().trim()
        val noAktaAlm = binding.edtNoAktaLahirAlm.text.toString().trim()
        val tglMati = binding.edtTanggalKematian.text.toString().trim()
        val tempatMati = binding.edtTempatKematian.text.toString().trim()

        val namaSaksi = binding.edtNamaSaksi.text.toString().trim()
        val nikSaksi = binding.edtNikSaksi.text.toString().trim()
        val noHpSaksi = binding.edtNoHpSaksi.text.toString().trim()
        val alamatSaksi = binding.edtAlamatSaksi.text.toString().trim()

        if (namaAlm.isEmpty() || nikAlm.length != 16 || namaSaksi.isEmpty() || !binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), "Lengkapi data dan centang persetujuan", Toast.LENGTH_SHORT).show()
            return
        }

        if (uriKtpAlm == null || uriKkAlm == null || uriAktaLahirAlm == null || uriKtpSaksi == null) {
            Toast.makeText(requireContext(), "Harap lampirkan semua dokumen persyaratan", Toast.LENGTH_SHORT).show()
            return
        }

        showConfirmationDialog(namaAlm, nikAlm, tglMati, namaSaksi, nikSaksi, noHpSaksi)
    }

    private fun showConfirmationDialog(namaAlm: String, nikAlm: String, tgl: String, namaSaksi: String, nikSaksi: String, hp: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<android.widget.TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnKirim)

        val summary = """
            Data Almarhum:
            Nama: $namaAlm
            NIK: $nikAlm
            Tgl Kematian: $tgl
            
            Data Saksi:
            Nama: $namaSaksi
            NIK: $nikSaksi
            No HP: $hp
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener { dialog.dismiss() }
        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData()
        }
        dialog.show()
    }

    private fun submitData() {
        val fileKtpAlm = FileUtils.uriToFile(uriKtpAlm!!, requireContext())
        val fileKkAlm = FileUtils.uriToFile(uriKkAlm!!, requireContext())
        val fileAktaLahirAlm = FileUtils.uriToFile(uriAktaLahirAlm!!, requireContext())
        val fileKtpSaksi = FileUtils.uriToFile(uriKtpSaksi!!, requireContext())

        val pengajuan = PengajuanAktaKematian(
            jenisLayanan = "Pendaftaran Akta Kematian",
            namaAlm = binding.edtNama.text.toString().trim(),
            nikAlm = binding.edtNikAlm.text.toString().trim(),
            noKKAlm = binding.edtNoKKAlm.text.toString().trim(),
            noAktaAlm = binding.edtNoAktaLahirAlm.text.toString().trim(),
            nameSaksi = binding.edtNamaSaksi.text.toString().trim(),
            nikSaksi = binding.edtNikSaksi.text.toString().trim(),
            noHpSaksi = binding.edtNoHpSaksi.text.toString().trim(),
            alamatSaksi = binding.edtAlamatSaksi.text.toString().trim(),
            docKTPAlm = fileKtpAlm.absolutePath,
            docKKAlm = fileKkAlm.absolutePath,
            docAktaKelahiran = fileAktaLahirAlm.absolutePath,
            docKTPSaksi = fileKtpSaksi.absolutePath
        )

        viewModel.insertAktaKematian(pengajuan)
        Toast.makeText(requireContext(), "Pengajuan Akta Kematian berhasil dikirim!", Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

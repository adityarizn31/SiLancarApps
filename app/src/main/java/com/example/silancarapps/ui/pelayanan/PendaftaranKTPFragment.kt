package com.example.silancarapps.ui.pelayanan

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
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.databinding.FragmentPendaftaranKTPBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import com.example.silancarapps.utils.ValidateKTP

class PendaftaranKTPFragment : Fragment() {

    private var _binding: FragmentPendaftaranKTPBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var uriKtp: Uri? = null
    private var uriKK: Uri? = null

    private val launcherKtp = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKtp = uri
            binding.tvKtp.text = "File terpilih"
        }
    }

    private val launcherKK = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriKK = uri
            binding.tvKk.text = "File terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranKTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnUploadKTP.setOnClickListener { launcherKtp.launch("image/*") }
        binding.btnUploadKK.setOnClickListener { launcherKK.launch("image/*") }

        binding.btnKirim.setOnClickListener {
            validateAndProcess()
        }
    }

    private fun validateAndProcess() {
        val nama = binding.edtNama.text.toString().trim()
        val nik = binding.edtNik.text.toString().trim()
        val noKK = binding.edtNoKK.text.toString().trim()
        val noHp = binding.edtNoHp.text.toString().trim()
        val alamat = binding.edtAlamat.text.toString().trim()

        // 1. Validasi Teks via Object
        val nameErr = ValidateKTP.getNameError(nama)
        val nikErr = ValidateKTP.getNikError(nik)
        val noKKErr = ValidateKTP.getNoKKError(noKK)
        val noHpErr = ValidateKTP.getNoHpError(noHp)
        val alamatErr = ValidateKTP.getAlamatError(alamat)

        // 2. Tampilkan Error di UI
        binding.edtNama.error = nameErr?.let { getString(it) }
        binding.edtNik.error = nikErr?.let { getString(it) }
        binding.edtNoKK.error = noKKErr?.let { getString(it) }
        binding.edtNoHp.error = noHpErr?.let { getString(it) }
        binding.edtAlamat.error = alamatErr?.let { getString(it) }

        val hasError = listOf(nameErr, nikErr, noKKErr, noHpErr, alamatErr).any { it != null }

        if (hasError) {
            Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
            return
        }

        if (!binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), getString(R.string.err_empty_persetujuan), Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Cek Lampiran
        if (uriKtp == null || uriKK == null) {
            Toast.makeText(requireContext(), getString(R.string.harap_lampirkan_dokumen), Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Konfirmasi
        showConfirmationDialog(nama, nik, noKK, noHp, alamat)
    }

    private fun showConfirmationDialog(nama: String, nik: String, noKK: String, noHp: String, alamat: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<android.widget.TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnKirim)

        val summary = """
            Nama: $nama
            NIK: $nik
            No KK: $noKK
            No HP: $noHp
            Alamat: $alamat
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener { dialog.dismiss() }
        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData(nama, nik, noKK, noHp, alamat)
        }
        dialog.show()
    }

    private fun submitData(nama: String, nik: String, noKK: String, noHp: String, alamat: String) {
        val fileKtp = FileUtils.uriToFile(uriKtp!!, requireContext())
        val fileKK = FileUtils.uriToFile(uriKK!!, requireContext())

        val pengajuan = PengajuanKTP(
            jenisLayanan = "Pendaftaran Kartu Tanda Penduduk",
            nama = nama,
            nik = nik,
            noKK = noKK,
            noHp = noHp,
            alamat = alamat,
            docKTP = fileKtp.absolutePath,
            docKK = fileKK.absolutePath
        )
        viewModel.insertKTP(pengajuan)
        Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

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
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import com.example.silancarapps.utils.ValidateKTP

class PendaftaranKTPFragment : Fragment() {

    private var _binding: FragmentPendaftaranKTPBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengajuanViewModel by viewModels {
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

        val isNameValid = ValidateKTP.isValidName(nama)
        val isNikValid = nik.length == 16 
        val isNoKKValid = ValidateKTP.isValidNoKK(noKK)
        val isNoHpValid = ValidateKTP.isValidNoHp(noHp)
        val isAlamatValid = ValidateKTP.isValidAlamat(alamat)

        if (!isNameValid || !isNikValid || !isNoKKValid || !isNoHpValid || !isAlamatValid || !binding.cbPersetujuan.isChecked) {
            Toast.makeText(requireContext(), "Lengkapi data dan centang persetujuan", Toast.LENGTH_SHORT).show()
            return
        }

        if (uriKtp == null || uriKK == null) {
            Toast.makeText(requireContext(), "Harap lampirkan dokumen persyaratan", Toast.LENGTH_SHORT).show()
            return
        }

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
            jenisLayanan = "Pendaftaran KTP",
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

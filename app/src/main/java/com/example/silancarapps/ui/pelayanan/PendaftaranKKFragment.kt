package com.example.silancarapps.ui.pelayanan

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
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.databinding.FragmentPendaftaranKKBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.FileUtils
import com.example.silancarapps.utils.ValidateKK

class PendaftaranKKFragment : Fragment() {

    private var _binding : FragmentPendaftaranKKBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var ktpSuami: android.net.Uri? = null
    private var ktpIstri: android.net.Uri? = null
    private var kkSuami: android.net.Uri? = null
    private var kkIstri: android.net.Uri? = null

    private val launcherIntentKtpSuami = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            ktpSuami = uri
            binding.tvKTPSuami.text = "File terpilih"
        }
    }

    private val launcherIntentKTPIstri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            ktpIstri = uri
            binding.tvKTPIstri.text = "File terpilih"
        }
    }

    private val launcherIntentKKSuami = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            kkSuami = uri
            binding.tvKKSuami.text = "File terpilih"
        }
    }

    private val launcherIntentKKIstri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            kkIstri = uri
            binding.tvKKIstri.text = "File terpilih"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranKKBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnUploadKTPSuami.setOnClickListener {
            launcherIntentKtpSuami.launch("image/*")
        }

        binding.btnUploadKTPIstri.setOnClickListener {
            launcherIntentKTPIstri.launch("image/*")
        }

        binding.btnUploadKKSuami.setOnClickListener {
            launcherIntentKKSuami.launch("image/*")
        }

        binding.btnUploadKKIstri.setOnClickListener {
            launcherIntentKKIstri.launch("image/*")
        }

        binding.btnKirim.setOnClickListener {
            validateAndProcess()
        }
    }

    private fun validateAndProcess() {
        val nama = binding.edtNama.text.toString().trim()
        val nikSuami = binding.edtNikSuami.text.toString().trim()
        val nikIstri = binding.edtNikIstri.text.toString().trim()
        val noKKSuami = binding.edtNoKKSuami.text.toString().trim()
        val noKKIstri = binding.edtNoKKIstri.text.toString().trim()
        val noHp = binding.edtNoHp.text.toString().trim()
        val alamat = binding.edtAlamat.text.toString().trim()

        val isNameValid = ValidateKK.isValidName(nama)
        val isNikSuamiValid = ValidateKK.isValidNikSuami(nikSuami)
        val isNikIstriValid = ValidateKK.isValidNikIstri(nikIstri)
        val isNoKKSuamiValid = ValidateKK.isValidNoKKSuami(noKKSuami)
        val isNoKKIstriValid = ValidateKK.isValidNoKKIstri(noKKIstri)
        val isNoHpValid = ValidateKK.isValidNoHp(noHp)
        val isAlamatValid = ValidateKK.isValidAlamat(alamat)

        if (!isNameValid || !isNikSuamiValid || !isNikIstriValid || !isNoKKSuamiValid || !isNoKKIstriValid || !isNoHpValid || !isAlamatValid) {
            if (!isNameValid) binding.edtNama.error = "Nama tidak boleh kosong"
            if (!isNikSuamiValid) binding.edtNikSuami.error = "NIK Suami harus 16 digit angka"
            if (!isNikIstriValid) binding.edtNikIstri.error = "NIK Istri harus 16 digit angka"
            if (!isNoKKSuamiValid) binding.edtNoKKSuami.error = "No KK Suami tidak boleh kosong"
            if (!isNoKKIstriValid) binding.edtNoKKIstri.error = "No KK Istri tidak boleh kosong"
            if (!isNoHpValid) binding.edtNoHp.error = "Nomor HP harus 12 digit angka"
            if (!isAlamatValid) binding.edtAlamat.error = "Alamat tidak boleh kosong"
            
            if (ktpSuami == null || ktpIstri == null || kkSuami == null || kkIstri == null) {
                Toast.makeText(requireContext(), "Harap lampirkan dokumen", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
            }
        } else {
            if (ktpSuami == null || ktpIstri == null || kkSuami == null || kkIstri == null) {
                Toast.makeText(requireContext(), "Harap lampirkan semua dokumen", Toast.LENGTH_SHORT).show()
                return
            }

            showConfirmationDialog(nama, nikSuami, nikIstri, noKKSuami, noKKIstri, noHp, alamat)
        }
    }

    private fun showConfirmationDialog(
        nama: String, nikSuami: String, nikIstri: String,
        noKKSuami: String, noKKIstri: String, noHp: String, alamat: String
    ) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_konfirmasi_data, null)
        val tvSummary = dialogView.findViewById<android.widget.TextView>(R.id.tvSummary)
        val btnBatal = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnBatal)
        val btnKirim = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnKirim)

        val summary = """
            Nama Lengkap: $nama
            NIK Suami: $nikSuami
            NIK Istri: $nikIstri
            No KK Suami: $noKKSuami
            No KK Istri: $noKKIstri
            No HP: $noHp
            Alamat: $alamat
        """.trimIndent()

        tvSummary.text = summary

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        btnKirim.setOnClickListener {
            dialog.dismiss()
            submitData(nama, nikSuami, nikIstri, noKKSuami, noKKIstri, noHp, alamat)
        }

        dialog.show()
    }

    private fun submitData(
        nama: String, nikSuami: String, nikIstri: String,
        noKKSuami: String, noKKIstri: String, noHp: String, alamat: String
    ) {
        val fileKtpSuami = FileUtils.uriToFile(ktpSuami!!, requireContext())
        val fileKtpIstri = FileUtils.uriToFile(ktpIstri!!, requireContext())
        val fileKkSuami = FileUtils.uriToFile(kkSuami!!, requireContext())
        val fileKkIstri = FileUtils.uriToFile(kkIstri!!, requireContext())

        val pengajuan = PengajuanKK(
            jenisLayanan = "Pendaftaran KK",
            nama = nama,
            nikSuami = nikSuami,
            nikIstri = nikIstri,
            noKKSuami = noKKSuami,
            noKKIstri = noKKIstri,
            noHp = noHp,
            alamat = alamat,
            docKTPSuami = fileKtpSuami.absolutePath,
            docKTPIstri = fileKtpIstri.absolutePath,
            docKKSuami = fileKkSuami.absolutePath,
            docKKIstri = fileKkIstri.absolutePath
        )
        
        viewModel.insertKK(pengajuan)
        Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

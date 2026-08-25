package com.example.silancarapps.ui.pelayanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.R
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.databinding.FragmentPendaftaranKKBinding
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.ValidateKK

class PendaftaranKKFragment : Fragment() {

    private var _binding : FragmentPendaftaranKKBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengajuanViewModel by viewModels {
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
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
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
            val pengajuan = PengajuanKK(
                jenisLayanan = "Pendaftaran KK",
                nama = nama,
                nikSuami = nikSuami,
                nikIstri = nikIstri,
                noKKSuami = noKKSuami,
                noKKIstri = noKKIstri,
                noHp = noHp,
                alamat = alamat,
                docKTPSuami = ktpSuami.toString(),
                docKTPIstri = ktpIstri.toString(),
                docKKSuami = kkSuami.toString(),
                docKKIstri = kkIstri.toString()
            )
            
            viewModel.insertKK(pengajuan)
            Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

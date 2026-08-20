package com.example.silancarapps.ui.pelayanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.R
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.databinding.FragmentPendaftaranKTPBinding
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.ValidateKTP

class PendaftaranKTPFragment : Fragment() {

    private var _binding: FragmentPendaftaranKTPBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengajuanViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
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
        
        binding.btnKirim.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
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

        if (!isNameValid || !isNikValid || !isNoKKValid || !isNoHpValid || !isAlamatValid) {
            if (!isNameValid) binding.edtNama.error = "Nama tidak boleh kosong"
            if (!isNikValid) binding.edtNik.error = "NIK harus 16 digit angka"
            if (!isNoKKValid) binding.edtNoKK.error = "No KK tidak boleh kosong"
            if (!isNoHpValid) binding.edtNoHp.error = "Nomor HP harus 12 digit angka"
            if (!isAlamatValid) binding.edtAlamat.error = "Alamat tidak boleh kosong"
            
            Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
        } else {
            val pengajuan = PengajuanKTP(
                jenisLayanan = "Pendaftaran KTP",
                nama = nama,
                nik = nik,
                noKK = noKK,
                noHp = noHp,
                alamat = alamat
            )
            viewModel.insertKTP(pengajuan)
            Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

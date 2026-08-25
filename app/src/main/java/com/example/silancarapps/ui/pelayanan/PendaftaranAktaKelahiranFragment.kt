package com.example.silancarapps.ui.pelayanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.databinding.FragmentPendaftaranAktaKelahiranBinding
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.ValidateAktaKelahiran

class PendaftaranAktaKelahiranFragment : Fragment() {

    private var _binding : FragmentPendaftaranAktaKelahiranBinding? = null
    private val binding get() = _binding!!
    private val viewModel : PengajuanViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
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

        binding.btnKirim.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val name = binding.edtNama.text.toString().trim()
        val nik = binding.edtNik.text.toString().trim()
        val noKK = binding.edtNoKK.text.toString().trim()
        val nikSuami = binding.edtNikSuami.text.toString().trim()
        val nikIstri = binding.edtNikIstri.text.toString().trim()
        val noHp = binding.edtNoHp.text.toString().trim()
        val alamat = binding.edtAlamat.text.toString().trim()

        val isNameValid = ValidateAktaKelahiran.isValidName(name)
        val isNikValid = ValidateAktaKelahiran.isValidNik(nik)
        val isNikSuamiValid = ValidateAktaKelahiran.isValidNikSuami(nikSuami)
        val isNikIstriValid = ValidateAktaKelahiran.isValidNikIstri(nikIstri)
        val isNoKKSuamiValid = ValidateAktaKelahiran.isValidNoKKSuami(noKK)
        val isNoKKIstriValid = ValidateAktaKelahiran.isValidNoKKIstri(noKK)
        val isNoHpValid = ValidateAktaKelahiran.isValidNoHp(noHp)
        val isAlamatValid = ValidateAktaKelahiran.isValidAlamat(alamat)

        if (!isNameValid || !isNikValid || !isNikSuamiValid || !isNikIstriValid || !isNoKKSuamiValid || !isNoKKIstriValid || !isNoHpValid || !isAlamatValid) {
            if (!isNameValid) binding.edtNama.error = "Nama tidak boleh kosong"
            if (!isNikValid) binding.edtNik.error = "NIK harus 16 digit angka"
            if (!isNikSuamiValid) binding.edtNikSuami.error = "NIK Suami harus 16 digit angka"
            if (!isNikIstriValid) binding.edtNikIstri.error = "NIK Istri harus 16 digit angka"
            if (!isNoKKSuamiValid) binding.edtNikSuami.error = "No KK Suami tidak boleh kosong"
            if (!isNoKKIstriValid) binding.edtNikIstri.error = "No KK Istri tidak boleh kosong"
            if (!isNoHpValid) binding.edtNoHp.error = "Nomor HP harus 12 digit angka"
            if (!isAlamatValid) binding.edtAlamat.error = "Alamat tidak boleh kosong"

            Toast.makeText(requireContext(), "Harap isi semua data dengan benar", Toast.LENGTH_SHORT).show()
        } else {
            val pengajuan = PengajuanAktaKelahiran(
                jenisLayanan = "Pendaftaran Akta Kelahiran",
                nama = name,
                nik = nik,
                noKK = noKK,
                noKTPSuami = nikSuami,
                noKTPIstri = nikIstri,
                noHp = noHp,
                alamat = alamat,
                docKK = null,
                docKTPSuami = null,
                docKTPIstri = null
            )

            viewModel.insertAktaKelahiran(pengajuan)
            Toast.makeText(requireContext(), "Pengajuan berhasil", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

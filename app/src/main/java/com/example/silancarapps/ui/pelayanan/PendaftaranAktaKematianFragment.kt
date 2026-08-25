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
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.databinding.FragmentPendaftaranAktaKematianBinding
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.ValidateAktaKelahiran
import com.example.silancarapps.utils.ValidateAktaKematian

class PendaftaranAktaKematianFragment : Fragment() {

    private var _binding : FragmentPendaftaranAktaKematianBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PengajuanViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
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

        binding.btnKirim.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val nameAlm = binding.edtNama.text.toString().trim()
        val nikAlm = binding.edtNikAlm.text.toString().trim()
        val noKKAlm = binding.edtNoKKAlm.text.toString().trim()
        val noAktaAlm = binding.edtNoAktaLahirAlm.text.toString().trim()

        val nameSaksi = binding.edtNamaSaksi.text.toString().trim()
        val noNikAksi = binding.edtNikSaksi.text.toString().trim()
        val noHpSaksi = binding.edtNoHpSaksi.text.toString().trim()
        val alamatSaksi = binding.edtAlamatSaksi.text.toString().trim()

        val isNameAlmValid = ValidateAktaKematian.isValidNameAlm(nameAlm)
        val isNikAlmValid = ValidateAktaKematian.isValidNikAlm(nikAlm)
        val isNoKKAlmValid = ValidateAktaKematian.isValidNoKKAlm(noKKAlm)
        val isNoAktaLahirAlmValid = ValidateAktaKematian.isValidNoAktaLahirAlm(noAktaAlm)

        val isNameSaksiValid = ValidateAktaKematian.isValidNameSaksi(nameSaksi)
        val isNikSaksiValid = ValidateAktaKematian.isValidNikSaksi(noNikAksi)
        val isNoHpSaksiValid = ValidateAktaKematian.isValidNoHpSaksi(noHpSaksi)
        val isAlamatSaksiValid = ValidateAktaKematian.isValidAlamatSaksi(alamatSaksi)

        if (!isNameAlmValid || !isNikAlmValid || !isNoKKAlmValid || !isNoAktaLahirAlmValid || !isNameSaksiValid || !isNikSaksiValid || !isNoHpSaksiValid || !isAlamatSaksiValid) {
            if (!isNameAlmValid) binding.edtNama.error = "Nama tidak boleh kosong"
            if (!isNikAlmValid) binding.edtNikAlm.error = "NIK harus 16 digit angka"
            if (!isNoKKAlmValid) binding.edtNoKKAlm.error = "No KK tidak boleh kosong"
            if (!isNoAktaLahirAlmValid) binding.edtNoAktaLahirAlm.error = "No Akta Kelahiran tidak boleh kosong"
            if (!isNameSaksiValid) binding.edtNamaSaksi.error = "Nama tidak boleh kosong"
            if (!isNikSaksiValid) binding.edtNikSaksi.error = "NIK harus 16 digit angka"
            if (!isNoHpSaksiValid) binding.edtNoHpSaksi.error = "Nomor HP harus 12 digit angka"
            if (!isAlamatSaksiValid) binding.edtAlamatSaksi.error = "Alamat tidak boleh kosong"

            Toast.makeText(requireContext(), "Harap isi semua data dengan benar", Toast.LENGTH_SHORT).show()
        } else {
            val pengajuan = PengajuanAktaKematian(
                jenisLayanan = "Pendaftaran Akta Kelahiran",
                namaAlm = nameAlm,
                nikAlm = nikAlm,
                noKKAlm = noKKAlm,
                noAktaAlm = noAktaAlm,
                nameSaksi = nameSaksi,
                nikSaksi = noNikAksi,
                noHpSaksi = noHpSaksi,
                alamatSaksi = alamatSaksi,
                docKTPAlm = null,
                docKKAlm = null,
                docAktaKelahiran = null,
                docKTPSaksi = null
            )

            viewModel.insertAktaKematian(pengajuan)
            Toast.makeText(requireContext(), "Pengajuan berhasil", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
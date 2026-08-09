package com.example.silancarapps.ui.pelayanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.silancarapps.R
import com.example.silancarapps.data.local.AppDatabase
import com.example.silancarapps.data.local.Pengajuan
import com.example.silancarapps.databinding.FragmentPendaftaranKKBinding
import com.example.silancarapps.utils.ValidateKK
import kotlinx.coroutines.launch

class PendaftaranKKFragment : Fragment() {

    private var _binding : FragmentPendaftaranKKBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendaftaranKKBinding.inflate(inflater, container, false)
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

        val isNameValid = ValidateKK.isValidName(nama)
        val isNikValid = ValidateKK.isValidNik(nik)
        val isNoKkValid = ValidateKK.isValidNoKKLama(noKK)
        val isNoHpValid = ValidateKK.isValidNoHp(noHp)
        val isAlamatValid = ValidateKK.isValidAlamat(alamat)

        if (!isNameValid || !isNikValid || !isNoKkValid || !isNoHpValid || !isAlamatValid) {
            if (!isNameValid) binding.edtNama.error = "Nama tidak boleh kosong"
            if (!isNikValid) binding.edtNik.error = "NIK harus 16 digit angka"
            if (!isNoKkValid) binding.edtNoKK.error = "Nomor KK tidak boleh kosong"
            if (!isNoHpValid) binding.edtNoHp.error = "Nomor HP harus 12 digit angka"
            if (!isAlamatValid) binding.edtAlamat.error = "Alamat tidak boleh kosong"
            
            Toast.makeText(requireContext(), getString(R.string.harap_isi_data), Toast.LENGTH_SHORT).show()
        } else {
            saveToDatabase(nama, nik, noKK, noHp, alamat)
        }
    }

    private fun saveToDatabase(nama: String, nik: String, noKK: String, noHp: String, alamat: String) {
        val pengajuan = Pengajuan(
            jenisLayanan = "Pendaftaran KK",
            nama = nama,
            nik = nik,
            noKK = noKK,
            noHp = noHp,
            alamat = alamat
        )

        val database = AppDatabase.getDatabase(requireContext())
        val dao = database.pengajuanDao()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                dao.insertPengajuan(pengajuan)
                Toast.makeText(requireContext(), getString(R.string.pengajuan_berhasil), Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.silancarapps.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.silancarapps.R
import com.example.silancarapps.adapter.ListPendaftaranAdapter
import com.example.silancarapps.data.model.Pendaftaran
import com.example.silancarapps.databinding.FragmentHomeBinding
import com.example.silancarapps.utils.SessionManager

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        sessionManager = SessionManager(requireContext())
        
        setupHeader()
        setupRecyclerView()
        
        binding.btnAjukan.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pendaftaranKKFragment)
        }
    }

    private fun setupHeader() {
        val userName = sessionManager.getUserName()
        binding.txtHello.text = getString(R.string.hello, userName)
    }

    private fun setupRecyclerView() {
        val listPendaftarans = listOf(
            Pendaftaran(getString(R.string.pendaftaran_kartu_keluarga), R.drawable.ic_kartukeluarga),
            Pendaftaran(getString(R.string.pendaftaran_kartu_tanda_penduduk), R.drawable.ic_kartutandapenduduk),
            Pendaftaran(getString(R.string.pendaftaran_akta_kelahiran), R.drawable.ic_aktakelahiran),
            Pendaftaran(getString(R.string.pendaftaran_akta_kematian), R.drawable.ic_aktakematian),
            Pendaftaran(getString(R.string.pendaftaran_kartu_identitas_anak), R.drawable.ic_kia),
            Pendaftaran(getString(R.string.pendaftaran_pemanfaatan_data), R.drawable.ic_pelayanan_pemanfaatandata),
            Pendaftaran(
                getString(R.string.pendaftaran_perpindahan_datang_provinsi_dan_kabupaten),
                R.drawable.ic_suratpindah
            )
        )

        val adapter = ListPendaftaranAdapter(listPendaftarans) { position ->
            when(position) {
                0 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranKKFragment)
                1 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranKTPFragment)
                2 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranAktaKelahiranFragment)
                3 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranAktaKematianFragment)
                4 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranKartuIdentitasAnakFragment)
                5 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranPelayananPemanfaatanDataFragment)
                6 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranSuratPindahFragment)
            }
        }

        binding.rvMenuLayanan.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

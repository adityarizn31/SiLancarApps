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
        binding.txtHello.text = "Halo $userName 👋"
    }

    private fun setupRecyclerView() {
        val listPendaftarans = listOf(
            Pendaftaran("Kartu Keluarga", R.drawable.ic_kartukeluarga),
            Pendaftaran("Kartu Tanda Penduduk", R.drawable.ic_kartutandapenduduk),
            Pendaftaran("Akta Kelahiran", R.drawable.ic_aktakelahiran),
            Pendaftaran("Akta Kematian", R.drawable.ic_aktakematian),
            Pendaftaran("Kartu Identitas Anak", R.drawable.ic_kia),
            Pendaftaran("Pendaftaran Pemanfaatan Data", R.drawable.ic_pelayanan_pemanfaatandata),
            Pendaftaran("Surat Perpindahan/Datang Provinsi & Kabupaten", R.drawable.ic_suratpindah),

        )

        val adapter = ListPendaftaranAdapter(listPendaftarans) { position ->
            when(position) {
                0 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranKKFragment)
                1 -> findNavController().navigate(R.id.action_homeFragment_to_pendaftaranKTPFragment)
                2 -> findNavController().navigate(R.id.pendaftaranAktaKelahiranFragment)
                3 -> findNavController().navigate(R.id.pendaftaranAktaKematianFragment)
                4 -> findNavController().navigate(R.id.pendaftaranKartuIdentitasAnakFragment)
                5 -> findNavController().navigate(R.id.pendaftaranPelayananPemanfaatanDataFragment)
                6 -> findNavController().navigate(R.id.pendaftaranSuratPindahFragment)
            }
        }

        binding.rvMenu.apply {
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

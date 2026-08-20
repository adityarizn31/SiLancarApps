package com.example.silancarapps.ui.riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silancarapps.adapter.RiwayatKKAdapter
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.databinding.FragmentRiwayatBinding
import com.example.silancarapps.ui.viewmodel.PengajuanViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RiwayatKKAdapter

    private val viewModel: PengajuanViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        adapter = RiwayatKKAdapter(emptyList()) { pengajuan ->
            deletePengajuan(pengajuan)
        }
        binding.rvRiwayat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@RiwayatFragment.adapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allPengajuanKK.collect { list ->
                if (list.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvRiwayat.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvRiwayat.visibility = View.VISIBLE
                    adapter.updateData(list)
                }
            }
        }
    }

    private fun deletePengajuan(pengajuan: PengajuanKK) {
        viewModel.deleteKK(pengajuan)
        Toast.makeText(requireContext(), "Pengajuan dihapus", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

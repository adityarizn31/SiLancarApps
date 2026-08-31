package com.example.silancarapps.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.silancarapps.databinding.FragmentProfileBinding
import com.example.silancarapps.ui.auth.LoginActivity
import com.example.silancarapps.ui.viewmodel.AuthViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import com.example.silancarapps.utils.SessionManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        
        setupProfileData()
        observeViewModel()

        binding.btnLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun setupProfileData() {
        val email = sessionManager.getUserEmail()
        if (email != null && email.isNotEmpty()) {
            viewModel.login(email)
        }
    }

    private fun observeViewModel() {
        viewModel.loginUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvProfileName.text = user.nama
                binding.tvProfileEmail.text = user.email
                binding.tvInfoName.text = user.nama
                
                // Karena di entity User belum ada NIK dan No HP, 
                // kita biarkan dummy atau bisa dikosongkan dulu
                binding.tvInfoNik.text = "Belum diatur"
                binding.tvInfoPhone.text = "Belum diatur"
            }
        }
    }

    private fun performLogout() {
        sessionManager.logout()
        Toast.makeText(requireContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

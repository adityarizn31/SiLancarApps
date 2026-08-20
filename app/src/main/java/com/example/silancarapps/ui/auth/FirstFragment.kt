package com.example.silancarapps.ui.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.silancarapps.data.local.User
import com.example.silancarapps.databinding.FragmentFirstBinding
import com.example.silancarapps.ui.viewmodel.AuthViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            validateRegister()
        }

        binding.tvLogin.setOnClickListener {
            requireActivity().finish()
        }

        observeViewModel()
    }

    private fun validateRegister() {
        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val confirmPassword = binding.edtConfirmPassword.text.toString().trim()

        var isValid = true

        if (name.isEmpty()) {
            binding.edtName.error = "Nama lengkap harus diisi"
            isValid = false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Email tidak valid"
            isValid = false
        }
        if (password.length < 6) {
            binding.edtPassword.error = "Minimal 6 karakter"
            isValid = false
        }
        if (confirmPassword != password) {
            binding.edtConfirmPassword.error = "Password tidak cocok"
            isValid = false
        }

        if (isValid) {
            val user = User(email, name, password)
            viewModel.register(user)
        }
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Daftar Berhasil! Silakan Masuk", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

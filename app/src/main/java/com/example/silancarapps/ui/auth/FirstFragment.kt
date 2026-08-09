package com.example.silancarapps.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.silancarapps.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

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
            requireActivity().finish() // Kembali ke LoginActivity
        }
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

        if (email.isEmpty()) {
            binding.edtEmail.error = "Email harus diisi"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Format email tidak valid"
            isValid = false
        }

        if (password.length < 6) {
            binding.edtPassword.error = "Kata sandi minimal 6 karakter"
            isValid = false
        }

        if (confirmPassword != password) {
            binding.edtConfirmPassword.error = "Konfirmasi kata sandi tidak cocok"
            isValid = false
        }

        if (isValid) {
            // Simulasi Registrasi Berhasil
            Toast.makeText(requireContext(), "Pendaftaran Berhasil! Silakan Masuk", Toast.LENGTH_LONG).show()
            requireActivity().finish() // Kembali ke LoginActivity setelah daftar
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

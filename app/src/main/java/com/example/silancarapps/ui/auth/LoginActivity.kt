package com.example.silancarapps.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.silancarapps.MainActivity
import com.example.silancarapps.databinding.ActivityLoginBinding
import com.example.silancarapps.ui.viewmodel.AuthViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
        observeViewModel()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.tilEmail.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.tilPassword.error = "Kata sandi tidak boleh kosong"
                return@setOnClickListener
            }

            viewModel.login(email)
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.loginUser.observe(this) { user ->
            if (user != null) {
                if (user.password == binding.edtPassword.text.toString().trim()) {
                    Toast.makeText(this, "Login Berhasil, Halo ${user.nama}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    binding.tilPassword.error = "Kata sandi salah"
                }
            } else {
                binding.tilEmail.error = "Email tidak terdaftar"
            }
        }
    }
}

package com.example.silancarapps

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.silancarapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.riwayatFragment, R.id.profileFragment, R.id.settingsFragment),
            binding.drawerLayout
        )

        // Hubungkan NavController dengan Toolbar
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        
        // Hubungkan NavController dengan NavigationView (Drawer)
        binding.navDrawer.setupWithNavController(navController)
        binding.navDrawer.itemIconTintList = null

        // Hubungkan NavController dengan BottomNavigationView
        binding.bottomNav.setupWithNavController(navController)

        // Sembunyikan Toolbar/BottomNav pada fragment tertentu jika perlu
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.toolbar.visibility = View.GONE
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                }
                R.id.riwayatFragment, R.id.profileFragment, R.id.settingsFragment -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                    binding.toolbar.setBackgroundColor(getColor(R.color.primary))
                }
                else -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
            }
        }
        
        binding.fab.setOnClickListener {
            openWhatsApp()
        }
    }

    private fun openWhatsApp() {
        val phoneNumber = "628123456789" // Ganti dengan nomor WA admin beneran bang
        val message = getString(R.string.bantuan_whatsapp)
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
        
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp tidak terinstall", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

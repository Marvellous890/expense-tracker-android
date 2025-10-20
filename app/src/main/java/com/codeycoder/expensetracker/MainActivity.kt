package com.codeycoder.expensetracker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codeycoder.expensetracker.Utilities.TAG
import com.codeycoder.expensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        val splashScreen: SplashScreen = installSplashScreen() // todo: hmmmmmmmm

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootFrame) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.addTrans.setOnClickListener {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as androidx.navigation.fragment.NavHostFragment
            val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            val tag = currentFragment?.arguments?.getString("tag") ?: ""

            if (tag == "home_f") {
                navHostFragment.navController.navigate(R.id.action_homeFragment_to_addTransFragment)
                binding.navBar.visibility = View.GONE
            }

        }
    }

    fun getBinding(): ActivityMainBinding {
        return binding
    }
}
package com.codeycoder.expensetracker

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.codeycoder.expensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        val splashScreen: SplashScreen = installSplashScreen() // todo: hmmmmmmmm

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootFrame) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.addTrans.setOnClickListener {
            navigateTo(null)
        }
        if (viewModel.navigating) {
            binding.navBar.visibility = View.GONE
        }
    }

    fun getBinding(): ActivityMainBinding {
        return binding
    }

    fun navigateTo(direction: NavDirections?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as androidx.navigation.fragment.NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
        val tag = currentFragment?.arguments?.getString("tag") ?: ""
        val navController = navHostFragment.navController

        if (direction != null) {
            viewModel.navigating = true
            navController.navigate(direction)
        } else if (tag == "home_f") {
            viewModel.navigating = true
            navController.navigate(R.id.action_homeFragment_to_addTransFragment)
        }

        if (viewModel.navigating) {
            val anim = ObjectAnimator.ofFloat(binding.navBar, "alpha", 1f, 0f).setDuration(250)
            anim.doOnEnd { binding.navBar.visibility = View.GONE }
            anim.start()
        }
    }
}
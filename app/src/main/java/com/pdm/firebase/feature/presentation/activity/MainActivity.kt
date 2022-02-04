package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pdm.firebase.R
import com.pdm.firebase.databinding.ActivityMainBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigationView()
    }

    private fun initNavigationView() {
        val navView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.startFragment(R.id.homeFragment)
                }
                R.id.searchFragment -> {
                    navController.startFragment(R.id.homeFragment)
                }
                R.id.wishlistFragment -> {
                    navController.startFragment(R.id.homeFragment)
                }
                R.id.profileFragment -> {
                    navController.startFragment(R.id.profileFragment)
                }
                R.id.settingsFragment -> {
                    navController.startFragment(R.id.homeFragment)
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
            true
        }
    }

    private fun NavController.startFragment(currentFragment: Int) {
        if (this.currentDestination?.id != currentFragment) {
            this.navigate(currentFragment, null,
                NavOptions.Builder().apply {
                    setEnterAnim(R.anim.anim_fade_in)
                    setExitAnim(R.anim.anim_fade_out)
                    setPopEnterAnim(R.anim.anim_fade_in)
                    setPopExitAnim(R.anim.anim_fade_out)
                }.build()
            )
        }
    }
}
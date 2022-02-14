package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.pdm.firebase.R
import com.pdm.firebase.databinding.ActivitySettingsBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.socialNavHost) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.social_nav)

        when (intent.getStringExtra("Navigation")) {
            "Settings" -> {
                //graph.setStartDestination(R.id.settingsFragment)
            }
            "Rate" -> {
                //graph.setStartDestination(R.id.rateFragment)
            }
            "Help" -> {
                //graph.setStartDestination(R.id.helpFragment)
            }
        }

        navHostFragment.navController.graph = graph
    }
}
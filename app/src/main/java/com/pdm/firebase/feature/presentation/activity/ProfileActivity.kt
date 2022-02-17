package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.pdm.firebase.R
import com.pdm.firebase.databinding.ActivityLoginBinding
import com.pdm.firebase.databinding.ActivityProfileBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.profileNavHost) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.profile_nav)

        when (intent.getStringExtra("Navigation")) {
            "Profile" -> {
                graph.setStartDestination(R.id.profileFragment)
            }
            "Notification" -> {
                //graph.setStartDestination(R.id.notificationFragment)
            }
        }

        navHostFragment.navController.graph = graph
    }
}
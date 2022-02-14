package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.pdm.firebase.R
import com.pdm.firebase.databinding.ActivitySocialBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class SocialActivity : BaseActivity() {

    private lateinit var binding: ActivitySocialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.socialNavHost) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.social_nav)

        when (intent.getStringExtra("Navigation")) {
            "Post" -> {
                graph.setStartDestination(R.id.postFragment)
            }
            "Discover" -> {
                graph.setStartDestination(R.id.discoverFragment)
            }
        }

        navHostFragment.navController.graph = graph
    }
}
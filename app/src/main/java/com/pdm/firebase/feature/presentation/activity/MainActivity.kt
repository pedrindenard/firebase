package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.R
import com.pdm.firebase.databinding.ActivityMainBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity
import com.pdm.firebase.util.DRAWER
import com.pdm.firebase.util.launchActivity
import com.pdm.firebase.util.setMenu

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        val navController = navHostFragment.navController
        navDrawerLayout = binding.drawerLayout
        navController.initNavigationDrawer()
        navController.initNavigationView()
        navController.initNavAppBar()
        navController.initListener()
        Firebase.auth.initNavItems()
    }

    private fun NavController.initNavigationView() {
        val navView = binding.bottomNavigation
        navView.setupWithNavController(this)
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    this.startFragment(R.id.homeFragment)
                }
                R.id.searchFragment -> {
                    this.startFragment(R.id.genderFragment)
                }
                R.id.postFragment -> {
                    launchActivity<SocialActivity>(extra = "Post")
                }
                R.id.socialFragment -> {
                    launchActivity<SocialActivity>(extra = "Discover")
                }
                R.id.chatFragment -> {
                    launchActivity<ProfileActivity>(extra = "Profile")
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
            true
        }
    }

    private fun NavController.initNavigationDrawer() {
        val navDrawer = binding.navDrawer
        val toggle = ActionBarDrawerToggle(this@MainActivity, navDrawerLayout, DRAWER, DRAWER)
        navDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeApp -> {
                    this.startFragment(R.id.homeFragment)
                }
                R.id.profileApp -> {
                    launchActivity<ProfileActivity>(extra = "Profile")
                }
                R.id.notificationApp -> {
                    launchActivity<ProfileActivity>(extra = "Notification")
                }
                R.id.loginRegisterApp -> {
                    launchActivity<LoginActivity>()
                }
                R.id.myListApp -> {
                    //this.startFragment(R.id.myListFragment)
                }
                R.id.logoutApp -> {
                    Firebase.auth.signOut()
                    launchActivity<MainActivity>()
                    finish()
                }
                R.id.settingsApp -> {
                    launchActivity<SettingsActivity>(extra = "Settings")
                }
                R.id.rateApp -> {
                    launchActivity<SettingsActivity>(extra = "Rate")
                }
                R.id.shareApp -> {
                    //Share intent
                }
                R.id.helpApp -> {
                    launchActivity<SettingsActivity>(extra = "Help")
                }
                R.id.polityApp -> {
                    launchActivity<PrivacyActivity>()
                }
                R.id.exitApp -> {
                    this@MainActivity.finishAffinity()
                }
            }
            navDrawerLayout.closeDrawer(GravityCompat.START, true)
            true
        }
        navDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun NavController.initNavAppBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    this.startFragment(R.id.searchFragment)
                }
            }
            true
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(
                GravityCompat.START,
                true
            )
        }
    }

    private fun FirebaseAuth.initNavItems() {
        when (this.currentUser != null) {
            true -> {
                binding.navDrawer.run {
                    setMenu(position = 1, isVisible = true)
                    setMenu(position = 2, isVisible = true)
                    setMenu(position = 3, isVisible = false)
                    setMenu(position = 4, isVisible = true)
                    setMenu(position = 5, isVisible = true)
                }
            }
            false -> {
                binding.navDrawer.run {
                    setMenu(position = 1, isVisible = false)
                    setMenu(position = 2, isVisible = false)
                    setMenu(position = 4, isVisible = false)
                    setMenu(position = 5, isVisible = false)
                }
            }
        }
    }

    private fun NavController.initListener() {
        this.addOnDestinationChangedListener { _, _, _ ->

        }
    }

    override fun onBackPressed() {
        when {
            navDrawerLayout.isDrawerOpen(GravityCompat.START) -> {
                navDrawerLayout.closeDrawer(
                    GravityCompat.START,
                    true
                )
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun NavController.startFragment(currentFragment: Int) {
        if (this.currentDestination?.id != currentFragment) {
            this.navigate(
                currentFragment, null,
                NavOptions.Builder().apply {
                    setEnterAnim(R.anim.fade_in)
                    setExitAnim(R.anim.fade_out)
                    setPopEnterAnim(R.anim.fade_in)
                    setPopExitAnim(R.anim.fade_out)
                }.build()
            )
        }
    }
}
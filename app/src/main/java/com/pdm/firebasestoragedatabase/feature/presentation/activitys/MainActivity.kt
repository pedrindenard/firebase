package com.pdm.firebasestoragedatabase.feature.presentation.activitys

import android.os.Bundle
import com.pdm.firebasestoragedatabase.databinding.ActivityMainBinding
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
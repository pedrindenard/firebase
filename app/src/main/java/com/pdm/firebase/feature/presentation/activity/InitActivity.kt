package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pdm.firebase.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
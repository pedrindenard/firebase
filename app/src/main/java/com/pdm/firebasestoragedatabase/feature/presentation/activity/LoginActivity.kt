package com.pdm.firebasestoragedatabase.feature.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pdm.firebasestoragedatabase.databinding.ActivityLoginBinding
import com.pdm.firebasestoragedatabase.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
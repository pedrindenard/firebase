package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import com.pdm.firebase.databinding.ActivityLoginBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
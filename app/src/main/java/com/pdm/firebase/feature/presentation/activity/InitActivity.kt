package com.pdm.firebase.feature.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pdm.firebase.databinding.ActivityInitBinding
import com.pdm.firebase.util.handlerDelay

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initIntroActivity()
    }

    private fun initIntroActivity() {
        handlerDelay(delayMillis = 2000) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
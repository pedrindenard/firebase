package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import com.pdm.firebase.databinding.ActivityPrivacyBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class PrivacyActivity : BaseActivity() {

    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
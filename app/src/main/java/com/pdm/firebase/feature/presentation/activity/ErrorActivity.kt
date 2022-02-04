package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import com.pdm.firebase.databinding.ActivityErrorBinding
import com.pdm.firebase.feature.presentation.base.BaseActivity

class ErrorActivity : BaseActivity() {

    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        onClickListener()
    }

    private fun onClickListener() {
        binding.appCompatButton.setOnClickListener {
            this.finish()
        }
    }
}
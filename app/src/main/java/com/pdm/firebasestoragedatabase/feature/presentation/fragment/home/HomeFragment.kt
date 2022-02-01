package com.pdm.firebasestoragedatabase.feature.presentation.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentHomeBinding
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.util.BLACK
import com.pdm.firebasestoragedatabase.util.hasPermissions
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener
import com.pdm.firebasestoragedatabase.util.storagePermissions

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        onRequestPermission()
    }

    private fun initClickListeners() {
        binding.downloadBtn.setOnSingleClickListener {

        }

        binding.uploadBtn.setOnSingleClickListener {

        }

        binding.readBtn.setOnSingleClickListener {

        }

        binding.crudBtn.setOnSingleClickListener {

        }

        binding.listBtn.setOnSingleClickListener {

        }
    }

    private fun onRequestPermission() {
        activity?.let {
            if (!hasPermissions(activity as Context, storagePermissions)) {
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                    it.entries.all { map ->
                        map.value
                    }.run {
                        if (!this) {
                            snackBar(
                                description = getString(R.string.home_message),
                                color = BLACK
                            )
                            activity?.finish()
                        }
                    }
                }.also {
                    it.launch(
                        storagePermissions
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
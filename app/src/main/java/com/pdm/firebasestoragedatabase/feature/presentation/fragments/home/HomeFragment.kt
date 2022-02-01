package com.pdm.firebasestoragedatabase.feature.presentation.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentHomeBinding
import com.pdm.firebasestoragedatabase.util.hasPermissions
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener
import com.pdm.firebasestoragedatabase.util.storagePermissions

class HomeFragment : Fragment() {

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
                            Toast.makeText(
                                context, getString(R.string.home_message),
                                Toast.LENGTH_LONG
                            ).show()
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
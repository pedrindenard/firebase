package com.pdm.firebasestoragedatabase.feature.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pdm.firebasestoragedatabase.databinding.DialogAlertBinding
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener

class DialogAlert(private val title: String, private val description: String) : DialogFragment() {

    private var _binding: DialogAlertBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = this.title
        binding.description.text = this.description
        binding.button.setOnSingleClickListener {
            dialog?.dismiss()
        }
    }
}
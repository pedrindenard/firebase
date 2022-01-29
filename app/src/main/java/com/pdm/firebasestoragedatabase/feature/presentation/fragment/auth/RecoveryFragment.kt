package com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentRecoveryBinding
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener

class RecoveryFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentRecoveryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitBtn.setOnSingleClickListener {
            val email = binding.emailField.text.toString()
            submitAddress(email = email)
            hideKeyboard()
        }
    }

    private fun submitAddress(email: String) {
        when {
            email.isEmpty() -> {
                binding.emailField.run {
                    error = getString(R.string.error_empty_field)
                    requestFocus()
                    return
                }
            }
            !isValidEmail(email) -> {
                binding.emailField.run {
                    error = getString(R.string.error_email)
                    requestFocus()
                    return
                }
            }
        }

        showProgressDialog()

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        context, getString(R.string.recovery_email_send),
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        context, it.exception!!.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                hideProgressDialog()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
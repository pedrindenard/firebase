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
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.viewModel.AuthViewModel
import com.pdm.firebasestoragedatabase.util.makeToast
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoveryFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<AuthViewModel>()
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
            viewModel.run {
                activity?.recoveryPassword(
                    firebaseAuth = firebaseAuth,
                    email = email
                )
            }
            showProgressDialog()
            hideKeyboard()
        }

        initObservers()
    }
    private fun initObservers() {
        viewModel.successRecoveryPassword.observe(viewLifecycleOwner, {
            activity?.makeToast(getString(R.string.recovery_email_send))
            findNavController().popBackStack()
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            activity?.makeToast(it)
            hideProgressDialog()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
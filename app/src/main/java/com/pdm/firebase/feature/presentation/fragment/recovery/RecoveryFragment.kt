package com.pdm.firebase.feature.presentation.fragment.recovery

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentRecoveryBinding
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.recovery.viewmodel.RecoveryViewModel
import com.pdm.firebase.util.RED
import com.pdm.firebase.util.setOnSingleClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoveryFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<RecoveryViewModel>()
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
        initClickListener()
        initObservers()
    }

    private fun initClickListener() {
        binding.confirm.setOnSingleClickListener {
            val email = binding.emailField.text.toString()
            viewModel.recoveryPassword(email)
            showProgressDialog()
            hideKeyboard()
        }

        binding.loginDescription.enterLogin.apply {
            text = getString(R.string.skip_back)
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            setOnSingleClickListener {
                findNavController().popBackStack()
                hideKeyboard()
            }
        }

        binding.emailField.showKeyBoard()
        binding.emailInput.defaultStateColor()
    }

    private fun initObservers() {
        viewModel.successRecoveryPassword.observe(viewLifecycleOwner, {
            showSnackBar(description = getString(R.string.recovery_email_send), RED)
            findNavController().popBackStack()
            hideProgressDialog()
        })

        viewModel.emailError.observe(viewLifecycleOwner, {
            binding.emailInput.setErrorInput(
                textInputEditText = binding.emailField,
                message = getString(R.string.error_email)
            )
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            showSnackBar(description = it, RED)
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            showSnackBar(description = getString(R.string.error_fields), RED)
            hideProgressDialog()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.pdm.firebase.feature.presentation.fragment.login.tabs

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentSignInBinding
import com.pdm.firebase.feature.presentation.activity.MainActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<SignInViewModel>()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.togglePassword.toggle(binding.passwordField)
        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.loginButton.setOnSingleClickListener {
            showProgressDialog()
            loginWithUser()
            hideKeyboard()
        }

        binding.resetPassword.setOnSingleClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToRecoveryFragment()
            )
            hideKeyboard()
        }

        binding.registerButton.setOnSingleClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
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

        binding.emailInput.defaultStateColor()
        binding.passwordInput.defaultStateColor()
    }

    private fun initObservers() {
        viewModel.emailError.observe(viewLifecycleOwner, {
            binding.emailInput.setErrorInput(
                textInputEditText = binding.emailField
            )
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            binding.passwordInput.setErrorInput(
                textInputEditText = binding.passwordField
            )
        })

        viewModel.successLoginWithUser.observe(viewLifecycleOwner, {
            hideProgressDialog()
            startMainActivity()
            emailIsVerified()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            it?.let {
                showSnackBar(
                    description = if (it.isNotEmpty()) {
                        it
                    } else {
                        getString(R.string.error_register_user)
                    },
                    color = RED
                )
                hideProgressDialog()
            }
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            it?.let {
                showSnackBar(description = getString(R.string.error_fields), RED)
                hideProgressDialog()
            }
        })
    }

    private fun loginWithUser() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        viewModel.loginWithUser(
            password = password,
            email = email
        )
    }

    private fun startMainActivity() {
        if (firebaseAuth.currentUser?.isEmailVerified == true) {
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }

    private fun emailIsVerified() {
        if (firebaseAuth.currentUser?.isEmailVerified == false) {
            showSnackBar(description = getString(R.string.sign_in_email_verify), BLACK)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
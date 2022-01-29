package com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentSignInBinding
import com.pdm.firebasestoragedatabase.feature.presentation.activity.MainActivity
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.viewModel.AuthViewModel
import com.pdm.firebasestoragedatabase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<AuthViewModel>()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        logIn()
    }

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
        binding.loginBtn.setOnSingleClickListener {
            AUTHENTICATION = DEFAULT
            showProgressDialog()
            onLoginFirebase()
            hideKeyboard()
        }

        binding.recoveryPassword.setOnSingleClickListener {
            findNavController().navigate(R.id.recoveryFragment)
            hideKeyboard()
        }

        binding.facebookLogin.setOnSingleClickListener {
            AUTHENTICATION = FACEBOOK
            hideKeyboard()
        }

        binding.googleLogin.setOnSingleClickListener {
            AUTHENTICATION = GOOGLE
            hideKeyboard()
        }

        binding.githubLogin.setOnSingleClickListener {
            AUTHENTICATION = GITHUB
            hideKeyboard()
        }

        binding.register.setOnSingleClickListener {
            findNavController().navigate(R.id.signUpFragment)
            hideKeyboard()
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.emailError.observe(viewLifecycleOwner, {
            binding.emailField.error = it
            binding.emailField.requestFocus()
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            binding.passwordField.error = it
            binding.passwordField.requestFocus()
        })

        viewModel.successLogin.observe(viewLifecycleOwner, {
            if (it != null && firebaseAuth.currentUser!!.isEmailVerified) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                activity?.makeToast(
                    getString(R.string.sign_in_email_verify)
                )
            }
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            activity?.makeToast(it)
            hideProgressDialog()
        })

        viewModel.invalidFields.observe(viewLifecycleOwner, {
            hideProgressDialog()
        })
    }

    private fun onLoginFirebase() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()

        viewModel.run {
            activity?.loginFirebase(
                password = password,
                email = email
            )
        }
    }

    private fun logIn() {
        if (
            firebaseAuth.currentUser != null &&
            firebaseAuth.currentUser!!.isEmailVerified
        ) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object {
        var AUTHENTICATION = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
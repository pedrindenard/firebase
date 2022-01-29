package com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentSignUpBinding
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.viewModel.AuthViewModel
import com.pdm.firebasestoragedatabase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<AuthViewModel>()
    private var _binding: FragmentSignUpBinding? = null
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerBtn.setOnSingleClickListener {
            AUTHENTICATION = DEFAULT
            onRegisterUserFirebase()
            showProgressDialog()
            hideKeyboard()
        }

        binding.googleRegister.setOnSingleClickListener {
            AUTHENTICATION = GOOGLE
            hideKeyboard()
        }

        binding.facebookRegister.setOnSingleClickListener {
            AUTHENTICATION = FACEBOOK
            hideKeyboard()
        }

        binding.githubRegister.setOnSingleClickListener {
            AUTHENTICATION = GITHUB
            hideKeyboard()
        }

        binding.dateField.formatToDate()
        initObservers()
    }

    private fun initObservers() {
        viewModel.fullNameEmpty.observe(viewLifecycleOwner, {
            binding.nameField.error = it
            binding.nameField.requestFocus()
        })

        viewModel.birthDateError.observe(viewLifecycleOwner, {
            binding.dateField.error = it
            binding.dateField.requestFocus()
        })

        viewModel.emailError.observe(viewLifecycleOwner, {
            binding.emailField.error = it
            binding.emailField.requestFocus()
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            binding.passwordField.error = it
            binding.passwordField.requestFocus()
        })

        viewModel.successCreateUser.observe(viewLifecycleOwner, {
            if (it.second == true) {
                viewModel.run {
                    activity?.editAuthenticationFirebase(
                        firebaseAuth = firebaseAuth,
                        user = it.first
                    )
                }
            }
        })

        viewModel.successEditUserInfo.observe(viewLifecycleOwner, {
            viewModel.run {
                activity?.submitEmailVerification(
                    firebaseAuth = firebaseAuth
                )
            }
        })

        viewModel.successRecoveryPassword.observe(viewLifecycleOwner, {
            activity?.makeToast(getString(R.string.recovery_email_send))
            findNavController().popBackStack()
        })

        viewModel.successSubmitEmailVerification.observe(viewLifecycleOwner, {
            activity?.makeToast(getString(R.string.sign_up_registered))
            findNavController().popBackStack()
        })

        viewModel.invalidFields.observe(viewLifecycleOwner, {
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            activity?.makeToast(it)
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            activity?.makeToast(getString(R.string.failure))
            hideProgressDialog()
        })
    }

    private fun onRegisterUserFirebase() {
        val name = binding.nameField.text.toString()
        val date = binding.dateField.text.toString()
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()

        viewModel.run {
            activity?.registerFirebase(
                password = password,
                email = email,
                name = name,
                date = date,
            )
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
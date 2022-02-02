package com.pdm.firebase.feature.presentation.fragment.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private val googleAuth = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                viewModel.loginWithGoogle(
                    data = result.data
                )
            }
            Activity.RESULT_CANCELED -> {
                hideProgressDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        login()
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
            showProgressDialog()
            loginWithUser()
            hideKeyboard()
        }

        binding.recoveryPassword.setOnSingleClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToRecoveryFragment()
            )
            hideKeyboard()
        }

        binding.socialNetwork.facebook.setOnSingleClickListener {
            showProgressDialog()
            hideKeyboard()
        }

        binding.socialNetwork.google.setOnSingleClickListener {
            showProgressDialog()
            loginWithGoogle()
            hideKeyboard()
        }

        binding.socialNetwork.github.setOnSingleClickListener {
            showProgressDialog()
            hideKeyboard()
        }

        binding.register.setOnSingleClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
            hideKeyboard()
        }

        binding.togglePassword.toggle(binding.passwordField)
        initObservers()
    }

    private fun initObservers() {
        viewModel.emailError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.emailInput,
                textInputEditText = binding.emailField,
                message = getString(R.string.error_email)
            )
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.passwordInput,
                textInputEditText = binding.passwordField,
                message = getString(R.string.error_password)
            )
        })

        viewModel.successLoginWithUser.observe(viewLifecycleOwner, {
            initMainActivity(
                condition = it.result != null && firebaseAuth.currentUser!!.isEmailVerified
            )
        })

        viewModel.successCreatedUserWithSocial.observe(viewLifecycleOwner, {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment(it)
            )
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            snackBar(description = it ?: getString(R.string.error_register_user), RED)
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            snackBar(description = getString(R.string.error_fields), RED)
            hideProgressDialog()
        })
    }

    private fun login() {
        initMainActivity(
            condition = firebaseAuth.currentUser != null && firebaseAuth.currentUser!!.isEmailVerified,
            isNull = null
        )
    }

    private fun loginWithUser() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        viewModel.loginWithUser(
            password = password,
            email = email
        )
    }

    private fun loginWithGoogle() {
        googleAuth.launch(
            GoogleSignIn.getClient(
                requireContext(), GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(SERVER_TOKEN_ID)
                    .requestEmail()
                    .build()
            ).signInIntent
        )
    }

    private fun initMainActivity(condition: Boolean, isNull: String? = "") {
        if (condition) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        } else if (isNull != null) {
            snackBar(
                description = getString(R.string.sign_in_email_verify),
                color = BLACK
            )
        }
        hideProgressDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
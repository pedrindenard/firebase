package com.pdm.firebasestoragedatabase.feature.presentation.fragments.login

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
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentSignInBinding
import com.pdm.firebasestoragedatabase.feature.presentation.activitys.MainActivity
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.feature.presentation.fragments.login.viewmodel.SignInViewModel
import com.pdm.firebasestoragedatabase.util.SERVER_TOKEN_ID
import com.pdm.firebasestoragedatabase.util.makeToast
import com.pdm.firebasestoragedatabase.util.setErrorInput
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<SignInViewModel>()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val googleAuth = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.loginWithGoogle(
                firebaseAuth = firebaseAuth,
                data = result.data
            )
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

        viewModel.successLoginWithSocial.observe(viewLifecycleOwner, {
            if (it.second) {
                initMainActivity(
                    condition = firebaseAuth.currentUser!!.isEmailVerified
                )
            } else {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToSignUpFragment(
                        value = false
                    )
                )
                hideProgressDialog()
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            activity?.makeToast(it)
            hideProgressDialog()
        })

        viewModel.invalidFields.observe(viewLifecycleOwner, {
            activity?.makeToast(getString(R.string.error_fields))
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
            activity?.makeToast(
                getString(R.string.sign_in_email_verify)
            )
        }
        hideProgressDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
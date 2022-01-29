package com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth

import android.content.Intent
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
import com.pdm.firebasestoragedatabase.databinding.FragmentSignInBinding
import com.pdm.firebasestoragedatabase.feature.presentation.activity.MainActivity
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.isValidPassword
import com.pdm.firebasestoragedatabase.util.setOnSingleClickListener

class SignInFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
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
            onLoginFirebase()
            hideKeyboard()
        }

        binding.recoveryPassword.setOnSingleClickListener {
            findNavController().navigate(R.id.recoveryFragment)
            hideKeyboard()
        }

        binding.facebookLogin.setOnSingleClickListener {
            hideKeyboard()
        }

        binding.googleLogin.setOnSingleClickListener {
            hideKeyboard()
        }

        binding.register.setOnSingleClickListener {
            findNavController().navigate(R.id.signUpFragment)
            hideKeyboard()
        }
    }

    private fun onLoginFirebase() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()

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

        when {
            password.isEmpty() -> {
                binding.passwordField.run {
                    error = getString(R.string.error_empty_field)
                    requestFocus()
                    return
                }
            }
            !isValidPassword(password) -> {
                binding.passwordField.run {
                    error = getString(R.string.error_password)
                    requestFocus()
                    return
                }
            }
        }

        showProgressDialog()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(
                        context, task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
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
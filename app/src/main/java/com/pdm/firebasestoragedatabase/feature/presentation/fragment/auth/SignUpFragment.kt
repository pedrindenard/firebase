package com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentSignUpBinding
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.presentation.activity.MainActivity
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.util.*

class SignUpFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

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
            onRegisterUserFirebase()
            hideKeyboard()
        }

        binding.dateField.formatToDate()
    }

    private fun onRegisterUserFirebase() {
        val name = binding.nameField.text.toString()
        val date = binding.dateField.text.toString()
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()

        if (name.isEmpty()) {
            binding.nameField.run {
                error = getString(R.string.error_empty_field)
                requestFocus()
                return
            }
        }

        when {
            date.isEmpty() -> {
                binding.dateField.run {
                    error = getString(R.string.error_empty_field)
                    requestFocus()
                    return
                }
            }
            !isValidBirthDate(date) -> {
                binding.dateField.run {
                    error = getString(R.string.error_date)
                    requestFocus()
                    return
                }
            }
        }

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

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    onSuccessCreateUser(
                        currentUser = firebaseAuth.currentUser,
                        email = email,
                        name = name,
                        age = date,
                    )
                } else {
                    hideProgressDialog()
                    onErrorRegister(task as Task<*>)
                }
            }
    }
    
    private fun onSuccessCreateUser(
        currentUser: FirebaseUser?, name: String, age: String, email: String
    ) {
        val user = User(
            name = name,
            email = email,
            age = age
        )
        
        FirebaseDatabase.getInstance().getReference(USERS_DATABASE)
            .child(currentUser?.uid ?: "")
            .setValue(user).addOnCompleteListener { 
                if (it.isSuccessful) {
                    Toast.makeText(
                        context, getString(R.string.sign_up_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                    onSuccessRegister()
                } else {
                    onErrorRegister(it as Task<*>)
                }
                hideProgressDialog()
            }
    }

    private fun onErrorRegister(it: Task<*>) {
        Toast.makeText(
            context, it.exception!!.message.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onSuccessRegister() {
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
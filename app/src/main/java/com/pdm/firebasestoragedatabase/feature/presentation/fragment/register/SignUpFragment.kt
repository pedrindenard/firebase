package com.pdm.firebasestoragedatabase.feature.presentation.fragment.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.databinding.FragmentSignUpBinding
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.presentation.activity.MainActivity
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseFragment
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.register.viewmodel.SignUpViewModel
import com.pdm.firebasestoragedatabase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val argument by navArgs<SignUpFragmentArgs>()
    private val viewModel by viewModel<SignUpViewModel>()
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
            showProgressDialog()
            hideKeyboard()
        }

        binding.collapsingContainer.appBarLayout.initCollapsingToolbar()
        binding.toggleConfirmPassword.toggle(binding.confirmPasswordField)
        binding.togglePassword.toggle(binding.passwordField)
        binding.legalDocumentField.formatToLegalDocument()
        binding.birthDateField.formatToDate()
        initObservers()
        updateUI()
    }

    private fun initObservers() {
        viewModel.nameEmpty.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.nameInput,
                textInputEditText = binding.nameField,
                appCompatTextView = binding.nameFieldError,
                message = getString(R.string.error_empty_field)
            )
        })

        viewModel.lastNameEmpty.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.lastNameInput,
                textInputEditText = binding.lastNameField,
                appCompatTextView = binding.lastNameFieldError,
                message = getString(R.string.error_empty_field)
            )
        })

        viewModel.birthDateError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.birthDateInput,
                textInputEditText = binding.birthDateField,
                appCompatTextView = binding.birthDateFieldError,
                message = getString(R.string.error_date)
            )
        })

        viewModel.emailError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.emailInput,
                textInputEditText = binding.emailField,
                appCompatTextView = binding.emailFieldError,
                message = getString(R.string.error_email)
            )
        })

        viewModel.legalDocumentError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.legalDocumentInput,
                textInputEditText = binding.legalDocumentField,
                appCompatTextView = binding.legalDocumentFieldError,
                message = getString(R.string.error_legal_document)
            )
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.passwordInput,
                textInputEditText = binding.passwordField,
                appCompatTextView = binding.passwordFieldError,
                message = getString(R.string.error_password)
            )
        })

        viewModel.confirmPasswordError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.confirmPasswordInput,
                textInputEditText = binding.confirmPasswordField,
                appCompatTextView = binding.confirmPasswordFieldError,
                message = getString(R.string.error_confirm_password_field)
            )
        })

        viewModel.successCreateUser.observe(viewLifecycleOwner, {
            snackBar(
                description = if (it == true) {
                    findNavController().popBackStack()
                    getString(R.string.sign_up_registered)
                } else {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    getString(R.string.sign_up_social)
                },
                BLACK
            )
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            snackBar(description = getString(R.string.error_fields), RED)
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            snackBar(description = it ?: getString(R.string.error_register_user), RED)
            hideProgressDialog()
        })
    }

    private fun onRegisterUserFirebase() {
        val name = binding.nameField.text.toString()
        val lastName = binding.lastNameField.text.toString()
        val birthDate = binding.birthDateField.text.toString()
        val email = binding.emailField.text.toString()
        val legalDocument = binding.legalDocumentField.text.toString()
        val password = binding.passwordField.text.toString()
        val confirmPassword = binding.confirmPasswordField.text.toString()

        if (argument.value == null) {
            viewModel.registerWithUser(
                user = User(
                    name = name,
                    lastName = lastName,
                    email = email,
                    legalDocument = legalDocument,
                    birthdate = birthDate,
                    gender = 1
                ),
                password = password,
                confirmPassword = confirmPassword,
                firebaseAuth = firebaseAuth
            )
        } else {
            viewModel.addInfoToUser(
                firebaseAuth = firebaseAuth,
                user = User(
                    name = name,
                    lastName = lastName,
                    email = email,
                    legalDocument = legalDocument,
                    birthdate = birthDate,
                    gender = 1
                )
            )
        }
    }

    private fun updateUI() {
        argument.value?.let {
            binding.emailInput.isVisible = false
            binding.passwordLayout.isVisible = false
            binding.confirmPasswordLayout.isVisible = false
            binding.emailFieldError.isVisible = false
            binding.passwordFieldError.isVisible = false
            binding.confirmPasswordFieldError.isVisible = false
            binding.nameField.text = it.firstName.toEditable()
            binding.lastNameField.text = it.familyName.toEditable()
            binding.emailField.text = it.email.toEditable()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
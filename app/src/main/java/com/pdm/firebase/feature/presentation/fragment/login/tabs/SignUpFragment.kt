package com.pdm.firebase.feature.presentation.fragment.login.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentSignUpBinding
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.presentation.activity.MainActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignUpViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    //private val argument by navArgs<SignUpFragmentArgs>()
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
            )
        })

        viewModel.lastNameEmpty.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.lastNameInput,
                textInputEditText = binding.lastNameField,
            )
        })

        viewModel.birthDateError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.birthDateInput,
                textInputEditText = binding.birthDateField,
            )
        })

        viewModel.emailError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.emailInput,
                textInputEditText = binding.emailField,
            )
        })

        viewModel.legalDocumentError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.legalDocumentInput,
                textInputEditText = binding.legalDocumentField,
            )
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.passwordInput,
                textInputEditText = binding.passwordField,
            )
        })

        viewModel.confirmPasswordError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.confirmPasswordInput,
                textInputEditText = binding.confirmPasswordField,
            )
        })

        viewModel.successCreateUser.observe(viewLifecycleOwner, {
            showSnackBar(description = if (it == true) {
                handlerDelay(delayMillis = 2000) {
                    findNavController().popBackStack()
                }
                getString(R.string.sign_up_registered)
            } else {
                handlerDelay(delayMillis = 2000) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                getString(R.string.sign_up_social)
            }, BLACK)

            activity?.onBackPressedListener(viewLifecycleOwner)
            binding.registerBtn.disableIt()
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            showSnackBar(description = getString(R.string.error_fields), RED)
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            showSnackBar(description = it ?: getString(R.string.error_register_user), RED)
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

        //if (argument.value == null) {
            viewModel.registerWithUser(
                user = User(
                    name = name,
                    fullName = lastName,
                    email = email,
                    legalDocument = legalDocument,
                    birthdate = birthDate,
                    gender = 1
                ),
                password = password,
                confirmPassword = confirmPassword,
                firebaseAuth = firebaseAuth
            )
//        } else {
//            viewModel.addInfoToUser(
//                firebaseAuth = firebaseAuth,
//                user = User(
//                    name = name,
//                    fullName = lastName,
//                    email = email,
//                    legalDocument = legalDocument,
//                    birthdate = birthDate,
//                    gender = 1
//                )
//            )
//        }
    }

    private fun updateUI() {
//        argument.value?.let {
//            binding.emailInput.isVisible = false
//            binding.passwordLayout.isVisible = false
//            binding.confirmPasswordLayout.isVisible = false
//            binding.nameField.text = it.firstName.toEditable()
//            binding.lastNameField.text = it.familyName.toEditable()
//            binding.emailField.text = it.email.toEditable()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
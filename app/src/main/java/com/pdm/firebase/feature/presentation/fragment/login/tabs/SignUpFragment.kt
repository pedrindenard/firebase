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
import com.pdm.firebase.feature.presentation.activity.PrivacyActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignUpViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

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
        initOnClickListener()
        initObservers()
    }

    private fun initOnClickListener() {
        binding.toggleConfirmPassword.toggle(binding.confirmPasswordField)
        binding.togglePassword.toggle(binding.passwordField)
        binding.birthDateField.formatToDate()
        binding.registerBtn.setOnSingleClickListener {
            onRegisterUserFirebase()
            showProgressDialog()
            hideKeyboard()
        }

        binding.privacyPolity.makeLinks(
            Pair(POLITY_PRIVACY, View.OnClickListener {
                val intent = Intent(context, PrivacyActivity::class.java)
                startActivity(intent)
            })
        )
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
        val password = binding.passwordField.text.toString()
        val confirmPassword = binding.confirmPasswordField.text.toString()

        viewModel.registerWithUser(
            user = User(
                name = name,
                lastName = ("$name $lastName"),
                email = email,
                birthdate = birthDate,
                gender = genderPicker()
            ),
            password = password,
            confirmPassword = confirmPassword,
            firebaseAuth = firebaseAuth
        )
    }

    private fun genderPicker(): Int {
        binding.genderPicker.checkedRadioButtonId.also {
            return when (it) {
                R.id.manRadioButton -> 1
                R.id.womanRadioButton -> 2
                R.id.otherRadioButton -> 3
                else -> 0
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
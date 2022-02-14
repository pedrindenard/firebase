package com.pdm.firebase.feature.presentation.fragment.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentProfileBinding
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<ProfileViewModel>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserInfo(firebaseAuth.uid)
        showProgressDialog()
        initClickListener()
        initObservers()
        initFields()
    }

    private fun initFields() {
        binding.nameInput.defaultStateColor()
        binding.emailInput.defaultStateColor()
        binding.birthDateInput.defaultStateColor()
        binding.numberInput.defaultStateColor()
        binding.passwordInput.defaultStateColor()
        binding.birthDateField.formatToDate()
    }

    private fun initClickListener() {
        binding.saveChanges.setOnSingleClickListener {
            showProgressDialog()
            editProfile()
        }

        binding.editPicture.setOnSingleClickListener {

        }

        binding.emailField.setOnSingleClickListener {

        }

        binding.birthDateField.setOnSingleClickListener {

        }

        binding.passwordField.setOnSingleClickListener {

        }
    }

    private fun initObservers() {
        viewModel.successGetUserInfo.observe(viewLifecycleOwner, {
            it?.let {
                binding.profileName.text = it.lastName.handlerEmptyFields(R.string.profile_name)
                binding.profileEmail.text = it.email.handlerEmptyFields(R.string.profile_email)
                binding.birthDateField.text = it.birthdate.toEditable()
                binding.numberField.text = it.numberPhone.toEditable()
                binding.nameField.text = it.lastName.toEditable()
                binding.emailField.text = it.email.toEditable()

                when (it.gender) {
                    1 -> binding.manRadioButton.isChecked = true
                    2 -> binding.womanRadioButton.isChecked = true
                    3 -> binding.otherRadioButton.isChecked = true
                }

                Glide.with(requireContext())
                    .load(it.picture)
                    .error(R.drawable.placeholder_profile)
                    .placeholder(R.drawable.placeholder_profile)
                    .into(binding.profilePicture)

                user = it
            }
            hideProgressDialog()
        })

        viewModel.successEditUserInfo.observe(viewLifecycleOwner, {
            showSnackBar(description = getString(R.string.profile_edit), color = BLACK)
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            showSnackBar(description = it, color = RED)
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            showSnackBar(description = it.message.toString(), color = RED)
            hideProgressDialog()
        })
    }

    private fun editProfile() {
        val fullName = binding.nameField.text.toString()
        val birthDate = binding.birthDateField.text.toString()

        viewModel.editProfile(
            firebaseAuth = firebaseAuth,
            user = User(
                name = user?.name ?: "",
                lastName = fullName,
                email = user?.email ?: "",
                birthdate = birthDate,
                picture = user?.picture ?: "",
                numberPhone = user?.numberPhone ?: "",
                gender = genderPicker()
            )
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

    private fun String.handlerEmptyFields(resource: Int): String {
        return takeIf { it.isNotEmpty() } ?: getString(resource)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
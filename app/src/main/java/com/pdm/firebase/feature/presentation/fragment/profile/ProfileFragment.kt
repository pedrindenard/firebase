package com.pdm.firebase.feature.presentation.fragment.profile

import android.content.Intent
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
import com.pdm.firebase.feature.presentation.activity.LoginActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebase.util.setOnSingleClickListener
import com.pdm.firebase.util.toEditable
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<ProfileViewModel>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        viewModel.getUserInfo(
            uid = firebaseAuth.uid
        )
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
        iniOnClickListeners()
        showProgressDialog()
        initObservers()
    }

    private fun iniOnClickListeners() {
        binding.profileSigOut.setOnSingleClickListener {
            viewModel.signOut(firebaseAuth)
        }
    }

    private fun initObservers() {
        viewModel.successGetUserInfo.observe(viewLifecycleOwner, {
            it?.let {
                binding.profileName.text = ("${it.name} ${it.lastName}")
                binding.profileEmail.text = it.email
                binding.nameField.text = it.lastName.toEditable()
                binding.emailField.text = it.email.toEditable()
                binding.birthDateField.text = it.birthdate.toEditable()
                binding.numberPhoneField.text = it.numberPhone.toEditable()

                Glide.with(requireContext())
                    .load(it.picture)
                    .error(R.drawable.ic_firebase)
                    .placeholder(R.drawable.ic_firebase)
                    .into(binding.profilePicture)
            }
            hideProgressDialog()
        })

        viewModel.successEditUserInfo.observe(viewLifecycleOwner, {
            hideProgressDialog()
        })

        viewModel.successLogOut.observe(viewLifecycleOwner, {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            hideProgressDialog()
            activity?.finish()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            hideProgressDialog()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            hideProgressDialog()
        })

        viewModel.errorLogout.observe(viewLifecycleOwner, {
            hideProgressDialog()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
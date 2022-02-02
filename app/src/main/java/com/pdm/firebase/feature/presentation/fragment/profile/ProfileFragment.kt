package com.pdm.firebase.feature.presentation.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.databinding.FragmentSignUpBinding
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModel<ProfileViewModel>()
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
        initObservers()
    }

    private fun initObservers() {
        viewModel.successGetUserInfo.observe(viewLifecycleOwner, {

        })

        viewModel.successEditUserInfo.observe(viewLifecycleOwner, {

        })

        viewModel.successLogOut.observe(viewLifecycleOwner, {

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
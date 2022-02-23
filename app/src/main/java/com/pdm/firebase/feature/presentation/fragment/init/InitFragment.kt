package com.pdm.firebase.feature.presentation.fragment.init

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdm.firebase.databinding.FragmentInitBinding
import com.pdm.firebase.feature.presentation.activity.IntroActivity
import com.pdm.firebase.feature.presentation.activity.MainActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.init.viewModel.SplashViewModel
import com.pdm.firebase.util.handlerDelay
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitFragment : BaseFragment() {

    private val viewModel by viewModel<SplashViewModel>()
    private var _binding: FragmentInitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
        initObservers()
    }

    private fun initObservers() {
        viewModel.startIntroActivity.observe(viewLifecycleOwner, { openAppOneTime ->
            if (openAppOneTime) {
                val intent = Intent(context, IntroActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

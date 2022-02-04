package com.pdm.firebase.feature.presentation.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentLoginBinding
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.login.adapter.LoginViewPage
import com.pdm.firebase.util.setOnSingleClickListener

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initTabLayout()
    }

    private fun initTabLayout() {
        val adapter = LoginViewPage(requireActivity().supportFragmentManager, lifecycle)
        val tabLayout = binding.loginTabLayout
        val viewPager = binding.loginViewPager
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { table, position ->
            when (position) {
                0 -> table.text = getString(R.string.sign_in_title)
                1 -> table.text = getString(R.string.sign_up_title)
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tabSelected: TabLayout.Tab?) {
                if (tabSelected != null) {
                    tabLayout.onTabAction(
                        style = R.style.loginTabSelect,
                        tab = tabSelected
                    )
                }
            }

            override fun onTabUnselected(tabSelected: TabLayout.Tab?) {
                if (tabSelected != null) {
                    tabLayout.onTabAction(
                        style = R.style.loginTabUnselect,
                        tab = tabSelected
                    )
                }
            }

            override fun onTabReselected(tabSelected: TabLayout.Tab?) {
                /** Do nothing here **/
            }
        })
    }

    private fun initClickListeners() {
        binding.firebaseProject.setOnSingleClickListener {

        }

        binding.popBackStack.setOnSingleClickListener {
            activity?.finish()
        }
    }

    private fun TabLayout.onTabAction(style: Int, tab: TabLayout.Tab) {
        val mainView = this.getChildAt(0) as ViewGroup
        val tabView = mainView.getChildAt(tab.position) as ViewGroup
        val tabViewChild = tabView.getChildAt(1) as TextView
        tabViewChild.setTextAppearance(style)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
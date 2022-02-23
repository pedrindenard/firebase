package com.pdm.firebase.feature.presentation.fragment.details.people

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdm.firebase.databinding.FragmentPeopleBinding
import com.pdm.firebase.feature.domain.model.credit.Cast
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.util.ARGS

class PeopleFragment : BaseFragment() {

    private lateinit var castDetails: Cast

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            castDetails = it.getSerializable(ARGS) as Cast
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
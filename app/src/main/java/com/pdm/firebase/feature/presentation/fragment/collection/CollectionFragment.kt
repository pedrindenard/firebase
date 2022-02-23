package com.pdm.firebase.feature.presentation.fragment.collection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdm.firebase.databinding.FragmentDiscoverBinding
import com.pdm.firebase.feature.domain.model.collection.Collection
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.collection.viewmodel.CollectionViewModel
import com.pdm.firebase.util.ARGS
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionFragment : BaseFragment() {

    private lateinit var args: Collection

    private val viewModel by viewModel<CollectionViewModel>()
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            args = it.getSerializable(ARGS) as Collection
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.openFilters.visibility = View.GONE
        initObservers()
        initAdapter()
    }

    private fun initObservers() {

    }

    private fun initAdapter() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
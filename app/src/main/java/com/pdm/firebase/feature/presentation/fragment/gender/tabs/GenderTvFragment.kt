package com.pdm.firebase.feature.presentation.fragment.gender.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentGenreBinding
import com.pdm.firebase.feature.domain.model.discovery.Discovery
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.gender.adapter.GenreAdapter
import com.pdm.firebase.feature.presentation.fragment.gender.viewmodel.GenderViewModel
import com.pdm.firebase.util.ARGS
import com.pdm.firebase.util.addImages
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenderTvFragment : BaseFragment() {

    private val viewModel by viewModel<GenderViewModel>()
    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGendersTv()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getGendersTV.observe(viewLifecycleOwner, {
            it.genres.run {
                addImages(param = "Tv")
                initGenderAdapter()
            }

            binding.includeLayout.shimmerEffect.apply {
                visibility = View.GONE
                stopShimmer()
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun List<Gender>.initGenderAdapter() {
        binding.recyclerViewGenre.adapter = GenreAdapter(
            mutableList = this@initGenderAdapter
        ).apply {
            setOnItemClickListener(object : GenreAdapter.ClickListener {
                override fun onItemClickListener(gender: Gender) {
                    findNavController().navigate(
                        R.id.discoverFragment,
                        Bundle().apply {
                            putSerializable(ARGS, Discovery(
                                gender = gender, param = "Tv"
                            ))
                        },
                        NavOptions.Builder().apply {
                            setEnterAnim(R.anim.fade_in)
                            setExitAnim(R.anim.fade_out)
                            setPopEnterAnim(R.anim.fade_in)
                            setPopExitAnim(R.anim.fade_out)
                        }.build()
                    )
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
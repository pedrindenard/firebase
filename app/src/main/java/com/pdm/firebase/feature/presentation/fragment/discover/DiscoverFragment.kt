package com.pdm.firebase.feature.presentation.fragment.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.arquitecture.Event.Companion.toMutable
import com.pdm.firebase.databinding.FragmentDiscoverBinding
import com.pdm.firebase.feature.domain.enums.GenreType
import com.pdm.firebase.feature.domain.model.discovery.Discovery
import com.pdm.firebase.feature.domain.model.discovery.Filter
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.discover.adapter.DiscoverAdapter
import com.pdm.firebase.feature.presentation.fragment.discover.viewmodel.DiscoveryViewModel
import com.pdm.firebase.util.ARGS
import com.pdm.firebase.util.setOnSingleClickListener
import com.pdm.firebase.util.startIntroAnimation
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverFragment : BaseFragment() {

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var discoveryAdapter: DiscoverAdapter

    private val viewModel by viewModel<DiscoveryViewModel>()
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private var filter: Filter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            val discovery = it.getSerializable(ARGS) as Discovery
            when (discovery.param) {
                GenreType.MOVIE.value -> {
                    viewModel.getMovies(id = discovery.gender.id)
                }
                GenreType.TV.value -> {
                    viewModel.getTvShows(id = discovery.gender.id)
                }
            }
            filter = Filter(
                genre = discovery.gender,
                id = discovery.gender.id
            )
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
        handlerProgressBar(isVisible = true)
        initAdapterDiscovery()
        initObservers()
        initActions()
    }

    private fun initActions() {
        binding.openFilters.apply {
            setOnSingleClickListener {
                binding.filterConstraint.startAnimation(anim = "in")
                this.startAnimation(anim = "out")
            }
        }

        binding.closeFilters.setOnSingleClickListener {
            binding.filterConstraint.startAnimation(anim = "out")
            binding.openFilters.startAnimation(anim = "in")
        }

        binding.filtersDiscover.setOnSingleClickListener {

        }

        binding.genresDiscover.apply {
            text = filter!!.genre.name
            setOnSingleClickListener {

            }
        }
    }

    private fun initObservers() {
        viewModel.getMovies.observe(viewLifecycleOwner, {
            discoveryAdapter.updateAdapter(mutableList = it.results.toMutable())
            handlerProgressBar(isVisible = false)
            setScrollListener(
                notIsLastPage = it.totalPage != it.currentPage,
                param = GenreType.MOVIE.value
            )
        })

        viewModel.getTvShows.observe(viewLifecycleOwner, {
            discoveryAdapter.updateAdapter(mutableList = it.results.toMutable())
            handlerProgressBar(isVisible = false)
            setScrollListener(
                notIsLastPage = it.totalPage != it.currentPage,
                param = GenreType.TV.value
            )
        })

//        viewModel.getGenres.observe(viewLifecycleOwner, {
//
//        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun initAdapterDiscovery() {
        binding.recyclerViewDiscover.apply {
            discoveryAdapter = DiscoverAdapter().apply {
                setOnItemClickListener(object : DiscoverAdapter.ClickListener {
                    override fun onItemClickListener(discovery: Search) {

                    }
                })
                setHasStableIds(true)
                adapter = this
            }
            startIntroAnimation()
        }
    }

    private fun setScrollListener(notIsLastPage: Boolean, param: String) {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemVisible = visibleItemCount + pastVisibleItems
                val totalItemCount = layoutManager.itemCount

                if ((totalItemVisible >= totalItemCount) && notIsLastPage) {
                    handlerProgressBar(isVisible = true)
                    handlerScrollListener()
                    when (param) {
                        GenreType.MOVIE.value -> {
                            viewModel.getMovies(
                                id = filter!!.id, sort = filter!!.sort
                            )
                        }
                        GenreType.TV.value -> {
                            viewModel.getTvShows(
                                id = filter!!.id, sort = filter!!.sort
                            )
                        }
                    }
                }
            }
        }
        binding.recyclerViewDiscover.addOnScrollListener(
            scrollListener
        )
    }

    private fun View.startAnimation(anim: String) {
        visibility = when (anim) {
            "in" -> {
                startAnimation(
                    AnimationUtils.loadAnimation(
                        activity, R.anim.from_right
                    )
                )
                View.VISIBLE
            }
            else -> {
                startAnimation(
                    AnimationUtils.loadAnimation(
                        activity, R.anim.to_right
                    )
                )
                View.GONE
            }
        }
    }

    private fun handlerScrollListener() {
        binding.recyclerViewDiscover.removeOnScrollListener(
            scrollListener
        )
    }

    private fun handlerProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.pdm.firebase.feature.presentation.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentHomeBinding
import com.pdm.firebase.feature.domain.model.actor.Actor
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.home.adapter.*
import com.pdm.firebase.feature.presentation.fragment.home.viewmodel.HomeViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel by viewModel<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttached()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRequestPermission()
        initObservers()
        initRefresh()
    }

    private fun initObservers() {
        viewModel.getSuperBanner.observe(viewLifecycleOwner, {
            val mutableList: MutableList<Movie> = mutableListOf()
            it.results.forEachIndexed { index, movie ->
                when (index < 5) {
                    true -> {
                        mutableList.add(
                            element = movie
                        )
                    }
                    else -> {
                        /** Do nothing **/
                    }
                }
            }
            mutableList.initBannerMovieAdapter()
        })

        viewModel.getPopularMovie.observe(viewLifecycleOwner, {
            it.results.initPopularMovieAdapter()

            binding.includeLayout.shimmerEffect.apply {
                visibility = View.GONE
                stopShimmer()
            }

            binding.refreshHomeFragment.apply {
                if (this.isRefreshing) {
                    this.isRefreshing = false
                }
            }
        })

        viewModel.getGendersMovie.observe(viewLifecycleOwner, {
            it.genres.run {
                first().isSelect = true
                initGenderAdapter()
                initMovieByGenres()
            }
        })

        viewModel.getMovieByGender.observe(viewLifecycleOwner, {
            it.results.initMovieAdapter()
        })

        viewModel.getUpcomingMovie.observe(viewLifecycleOwner, {
            it.results.initShowcaseAdapter()
        })

        viewModel.getBestActors.observe(viewLifecycleOwner, {
            it.results.initActorsAdapter()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun MutableList<Movie>.initBannerMovieAdapter() {
        binding.superBanner.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = BannerAdapter(mutableList = this@initBannerMovieAdapter).apply {
                setOnItemClickListener(object : BannerAdapter.ClickListener {
                    override fun onItemClickListener(movie: Movie) {

                    }
                })
            }
        }.also {
            binding.indicator.setViewPager(
                it
            )
        }
    }

    private fun MutableList<Movie>.initPopularMovieAdapter() {
        binding.popularRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = MovieAdapter(mutableList = this@initPopularMovieAdapter).apply {
                setOnItemClickListener(object : MovieAdapter.ClickListener {
                    override fun onItemClickListener(movie: Movie) {

                    }
                })
            }
        }
    }

    private fun List<Gender>.initGenderAdapter() {
        binding.menuMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = GenderAdapter(mutableList = this@initGenderAdapter).apply {
                setOnItemClickListener(object : GenderAdapter.ClickListener {
                    override fun onItemClickListener(gender: Gender) {
                        viewModel.getMovieByGender(
                            id = gender.id
                        )
                    }
                })
            }
        }
    }

    private fun MutableList<Movie>.initMovieAdapter() {
        movieAdapter = MovieAdapter(mutableList = this@initMovieAdapter).apply {
            setOnItemClickListener(object : MovieAdapter.ClickListener {
                override fun onItemClickListener(movie: Movie) {

                }
            })
        }
        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = movieAdapter
            startIntroAnimation()
        }
    }

    private fun MutableList<Movie>.initShowcaseAdapter() {
        binding.showcaseRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = ShowcaseAdapter(mutableList = this@initShowcaseAdapter).apply {
                setOnItemClickListener(object : ShowcaseAdapter.ClickListener {
                    override fun onItemClickListener(movie: Movie) {

                    }
                })
            }
        }
    }

    private fun MutableList<Actor>.initActorsAdapter() {
        binding.actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = ActorAdapter(mutableList = this@initActorsAdapter).apply {
                setOnItemClickListener(object : ActorAdapter.ClickListener {
                    override fun onItemClickListener(actor: Actor) {

                    }
                })
            }
        }
    }

    private fun List<Gender>.initMovieByGenres() {
        viewModel.getMovieByGender(
            id = this.first().id
        )
    }

    private fun onRequestPermission() {
        activity?.let {
            if (!hasPermissions(activity as Context, storagePermissions)) {
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                    it.entries.all { map ->
                        map.value
                    }.run {
                        if (!this) {
                            showSnackBar(
                                description = getString(R.string.home_message),
                                color = BLACK
                            )
                            activity?.finish()
                        }
                    }
                }.also {
                    it.launch(
                        storagePermissions
                    )
                }
            }
        }
    }

    private fun initRefresh() {
        binding.refreshHomeFragment.setSwipeRefresh {
            onAttached(cleanCache = true)
        }
    }

    private fun onAttached(cleanCache: Boolean? = false) {
        viewModel.getSuperBanner(cleanCache)
        viewModel.getPopularMovie(cleanCache)
        viewModel.getRatedMovie(cleanCache)
        viewModel.getGendersMovie(cleanCache)
        viewModel.getUpcomingMovie(cleanCache)
        viewModel.getBestActors(cleanCache)
        viewModel.addInfoOnCache()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
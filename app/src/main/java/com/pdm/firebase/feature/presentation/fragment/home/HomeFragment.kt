package com.pdm.firebase.feature.presentation.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentHomeBinding
import com.pdm.firebase.feature.domain.model.people.People
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.domain.model.tv.TvShow
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.home.adapter.*
import com.pdm.firebase.feature.presentation.fragment.home.viewmodel.HomeViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : BaseFragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var bannerTimer: Timer? = null
    private var scrollY: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initHomeData()
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
        initActions()
    }

    private fun initObservers() {
        viewModel.getUpcomingMovie.observe(viewLifecycleOwner, {
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
            binding.refreshHomeFragment.stopSwipe()
            binding.superBanner.initAutomaticSlide(
                it = mutableList
            )

            binding.includeLayout.shimmerEffect.apply {
                visibility = View.GONE
                stopShimmer()
            }
        })

        viewModel.getGendersMovie.observe(viewLifecycleOwner, { it ->
            it.genres.run {
                forEach { it.isSelect = false }
                first().isSelect = true
                initMovieGenderAdapter()
                initMovieByGenres()
            }
        })

        viewModel.getGendersTv.observe(viewLifecycleOwner, { it ->
            it.genres.run {
                forEach { it.isSelect = false }
                first().isSelect = true
                initTvGenderAdapter()
                initTvByGenres()
            }
        })

        viewModel.getMovieByGender.observe(viewLifecycleOwner, {
            binding.progressGenres.visibility = View.GONE
            it.results.initMovieAdapter()
        })

        viewModel.getTvShowByGender.observe(viewLifecycleOwner, {
            binding.progressTvGenres.visibility = View.GONE
            it.results.initTvAdapter()
        })

        viewModel.getTopRatedMovie.observe(viewLifecycleOwner, {
            it.results.initTopRatedMovieAdapter()
        })

        viewModel.getNowPlayingMovie.observe(viewLifecycleOwner, {
            it.results.initShowcaseAdapter()
        })

        viewModel.getPopularMovie.observe(viewLifecycleOwner, {
            it.results.initPopularMovieAdapter()
        })

        viewModel.getTvShowTopRated.observe(viewLifecycleOwner, {
            it.results.initTopRatedTvAdapter()
        })

        viewModel.getTvShowPopular.observe(viewLifecycleOwner, {
            it.results.initPopularTvAdapter()
        })

        viewModel.getTvShowOnAir.observe(viewLifecycleOwner, {
            it.results.initOnAirTvAdapter()
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
                        //upcomingScreen
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
                        initMovieDetails(it = movie)
                    }
                })
            }
        }
    }

    private fun MutableList<Movie>.initTopRatedMovieAdapter() {
        binding.topMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = MovieAdapter(mutableList = this@initTopRatedMovieAdapter).apply {
                setOnItemClickListener(object : MovieAdapter.ClickListener {
                    override fun onItemClickListener(movie: Movie) {
                        initMovieDetails(it = movie)
                    }
                })
            }
        }
    }

    private fun List<Gender>.initMovieGenderAdapter() {
        binding.menuMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = GenderAdapter(mutableList = this@initMovieGenderAdapter).apply {
                setOnItemClickListener(object : GenderAdapter.ClickListener {
                    override fun onItemClickListener(gender: Gender) {
                        binding.progressGenres.visibility = View.VISIBLE
                        viewModel.getMovieByGender(
                            ignoreCache = true,
                            id = gender.id
                        )
                    }
                })
            }
        }
    }

    private fun MutableList<TvShow>.initPopularTvAdapter() {
        binding.popularTvRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = TvAdapter(mutableList = this@initPopularTvAdapter).apply {
                setOnItemClickListener(object : TvAdapter.ClickListener {
                    override fun onItemClickListener(tvShow: TvShow) {
                        //tv
                    }
                })
            }
        }
    }

    private fun MutableList<TvShow>.initOnAirTvAdapter() {
        binding.showcaseTvRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = OnAirAdapter(mutableList = this@initOnAirTvAdapter).apply {
                setOnItemClickListener(object : OnAirAdapter.ClickListener {
                    override fun onItemClickListener(tvShow: TvShow) {
                        //youtube
                    }
                })
            }
        }
    }

    private fun List<Gender>.initTvGenderAdapter() {
        binding.menuTvRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = GenderAdapter(mutableList = this@initTvGenderAdapter).apply {
                setOnItemClickListener(object : GenderAdapter.ClickListener {
                    override fun onItemClickListener(gender: Gender) {
                        binding.progressTvGenres.visibility = View.VISIBLE
                        viewModel.getTvShowByGender(
                            ignoreCache = true,
                            id = gender.id
                        )
                    }
                })
            }
        }
    }

    private fun MutableList<TvShow>.initTvAdapter() {
        val tvAdapter = TvAdapter(mutableList = this@initTvAdapter).apply {
            setOnItemClickListener(object : TvAdapter.ClickListener {
                override fun onItemClickListener(tvShow: TvShow) {
                    //tv
                }
            })
        }
        binding.tvRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = tvAdapter
            startIntroAnimation()
        }
    }

    private fun MutableList<TvShow>.initTopRatedTvAdapter() {
        binding.topTvRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = TvAdapter(mutableList = this@initTopRatedTvAdapter).apply {
                setOnItemClickListener(object : TvAdapter.ClickListener {
                    override fun onItemClickListener(tvShow: TvShow) {
                        //tv
                    }
                })
            }
        }
    }

    private fun MutableList<Movie>.initMovieAdapter() {
        val movieAdapter = MovieAdapter(mutableList = this@initMovieAdapter).apply {
            setOnItemClickListener(object : MovieAdapter.ClickListener {
                override fun onItemClickListener(movie: Movie) {
                    initMovieDetails(it = movie)
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
                        //youtube
                    }
                })
            }
        }
    }

    private fun MutableList<People>.initActorsAdapter() {
        binding.actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), horizontalFadingEdgeLength, false)
            adapter = ActorAdapter(mutableList = this@initActorsAdapter).apply {
                setOnItemClickListener(object : ActorAdapter.ClickListener {
                    override fun onItemClickListener(people: People) {
                        findNavController().navigate(
                            R.id.peopleFragment, Bundle().apply {
                                putSerializable(ARGS, people.id)
                            }, NavOptions.Builder().apply {
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
    }

    private fun ViewPager2.initAutomaticSlide(it: MutableList<Movie>) {
        val timerTask = object : TimerTask() {
            override fun run() {
                this@initAutomaticSlide.post {
                    this@initAutomaticSlide.currentItem = (
                            this@initAutomaticSlide.currentItem + 1
                            ) % it.size
                }
            }
        }; onStopBanner()
        bannerTimer = Timer().apply {
            schedule(
                timerTask, 2000, 3000
            )
        }
    }

    private fun List<Gender>.initMovieByGenres() {
        viewModel.getMovieByGender(
            id = this.first().id
        )
    }

    private fun List<Gender>.initTvByGenres() {
        viewModel.getTvShowByGender(
            id = this.first().id
        )
    }

    private fun initActions() {
        binding.refreshHomeFragment.setSwipeRefresh {
            initHomeData(ignoreCache = true)
            scrollY = null
        }

        binding.homeContainer.waitForLayout {
            this@HomeFragment.scrollY?.let {
                binding.nestedScrollView.scrollTo(
                    0, it
                )
            }
        }
    }

    private fun initHomeData(ignoreCache: Boolean? = false) {
        viewModel.getNowPlayingMovie(ignoreCache = ignoreCache)
        viewModel.getPopularMovie(ignoreCache = ignoreCache)
        viewModel.getTopRatedMovie(ignoreCache = ignoreCache)
        viewModel.getGendersMovie(ignoreCache = ignoreCache)
        viewModel.getUpcomingMovie(ignoreCache = ignoreCache)
        viewModel.getBestActors(ignoreCache = ignoreCache)
        viewModel.getTvShowPopular(ignoreCache = ignoreCache)
        viewModel.getTvShowTopRated(ignoreCache = ignoreCache)
        viewModel.getGendersTvShow(ignoreCache = ignoreCache)
        viewModel.getTvShowOnAir(ignoreCache = ignoreCache)
        viewModel.addInfoOnCache()
    }

    private fun initMovieDetails(it: Movie) {
        findNavController().navigate(
            R.id.movieFragment, Bundle().apply {
                putSerializable(ARGS, it.id)
            }, NavOptions.Builder().apply {
                setEnterAnim(R.anim.fade_in)
                setExitAnim(R.anim.fade_out)
                setPopEnterAnim(R.anim.fade_in)
                setPopExitAnim(R.anim.fade_out)
            }.build()
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

    private fun onStopBanner() {
        bannerTimer?.cancel()
    }

    override fun onStop() {
        super.onStop()
        binding.nestedScrollView.let {
            scrollY = it.scrollY
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
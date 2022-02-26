package com.pdm.firebase.feature.presentation.fragment.details.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentMovieBinding
import com.pdm.firebase.feature.domain.model.credit.movie.MovieCredits
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.image.Image
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.domain.model.movie.details.MovieDetailsResponse
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCompany
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderFlatRate
import com.pdm.firebase.feature.domain.model.review.Review
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.video.Video
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.details.movie.adapter.*
import com.pdm.firebase.feature.presentation.fragment.details.movie.viewmodel.MovieViewModel
import com.pdm.firebase.feature.presentation.fragment.home.adapter.MovieAdapter
import com.pdm.firebase.feature.presentation.fragment.review.adapter.ReviewsAdapter
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MovieFragment : BaseFragment() {

    private val viewModel by viewModel<MovieViewModel>()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var movieId: Int? = null
    private var timer: Timer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            movieId = it.getSerializable(ARGS) as Int
            viewModel.initViewModel(
                id = movieId!!
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initActions()
    }

    private fun initActions() {
        binding.rateMovie.setOnSingleClickListener {

        }

        binding.shareMovie.setOnSingleClickListener {

        }

        binding.favMovie.setOnSingleClickListener {

        }

        binding.crewLabel.setOnSingleClickListener {

        }

        binding.seeMoreMovie.setOnSingleClickListener {

        }

        binding.reviewsMoreMovie.setOnSingleClickListener {

        }
    }

    private fun initObservers() {
        viewModel.getMovieDetails.observe(viewLifecycleOwner, {
            binding.titleMovie.text = it.title
            binding.movieRateStar.rating = it.voteAverage
            binding.originalTitleMovie.text = it.originalTitle
            binding.averageMovie.text = it.voteAverage.toString()
            binding.votesMovie.text = it.voteCount.toString()
            binding.premiereMovie.text = it.releaseDate
            binding.typeMovie.text = it.genres.formatGenres()
            binding.yearMovie.text = it.releaseDate.formatDate()
            initAdapterCompany(it.companies)
            initAdapterGender(it.genres)
            initMovieDetails(it)
        })

        viewModel.getMovieCredits.observe(viewLifecycleOwner, {
            binding.crewMovie.apply {
                it.crew.takeIf { it.isNotEmpty() }?.let { text = it.formatCrew() } ?: run {
                    visibility = View.GONE
                }
            }
            binding.actorsRecyclerView.apply {
                adapter = CastAdapter(it.cast).apply {
                    setOnItemClickListener(object : CastAdapter.ClickListener {
                        override fun onItemClickListener(people: MovieCredits) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieRecommendations.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.moreLikeThisRecyclerView.apply {
                    adapter = MovieAdapter(it).apply {
                        setOnItemClickListener(object : MovieAdapter.ClickListener {
                            override fun onItemClickListener(movie: Movie) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.moreLikeThisConstraint.visibility = View.GONE
            }
        })

        viewModel.getMovieProviders.observe(viewLifecycleOwner, { it ->
            it.results.formatToList().takeIf { !it.isNullOrEmpty() }?.let {
                binding.providersRecyclerView.apply {
                    adapter = ProviderAdapter(it).apply {
                        setOnItemClickListener(object : ProviderAdapter.ClickListener {
                            override fun onItemClickListener(provider: ProviderFlatRate) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.providersConstraint.visibility = View.GONE
            }
        })

        viewModel.getMovieVideos.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.videosRecyclerView.apply {
                    adapter = VideoAdapter(it).apply {
                        setOnItemClickListener(object : VideoAdapter.ClickListener {
                            override fun onItemClickListener(video: Video) {
                                findNavController().navigate(
                                    R.id.videoFragment, Bundle().apply {
                                    putSerializable(ARGS, video.key)
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
            } ?: run {
                binding.videosConstraint.visibility = View.GONE
            }
        })

        viewModel.getMovieSimilar.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.seeTooRecyclerView.apply {
                    adapter = SimilarAdapter().apply {
                        updateAdapter(mutableList = it)
                        setOnItemClickListener(object : SimilarAdapter.ClickListener {
                            override fun onItemClickListener(movie: Movie) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.seeTooConstraint.visibility = View.GONE
            }
        })

        viewModel.getMovieReviews.observe(viewLifecycleOwner, { it ->
            it.initMovieReviews().takeIf { !it.isNullOrEmpty() }?.let {
                binding.reviewsRecyclerView.apply {
                    adapter = ReviewsAdapter(maxLineContent = 3).apply {
                        updateAdapter(mutableList = it)
                        setOnItemClickListener(object : ReviewsAdapter.ClickListener {
                            override fun onItemClickListener(review: Review) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.reviewsConstraint.visibility = View.GONE
            }
        })

        viewModel.getMovieImages.observe(viewLifecycleOwner, { it ->
            it.backdrops.takeIf { !it.isNullOrEmpty() }?.let {
                binding.logoMovie.apply {
                    initAutomaticSlide(it = it)
                    orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    adapter = ImageAdapter(mutableList = it).apply {
                        setOnItemClickListener(object : ImageAdapter.ClickListener {
                            override fun onItemClickListener(image: Image) {

                            }
                        })
                    }
                }
            } ?: run {
                /** Do nothing here **/
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun initAdapterGender(it: List<Gender>) {
        it.takeIf { !it.isNullOrEmpty() }?.let {
            binding.genderMovieRecyclerView.apply {
                adapter = GenderAdapter(mutableList = it).apply {
                    setOnItemClickListener(object : GenderAdapter.ClickListener {
                        override fun onItemClickListener(gender: Gender) {

                        }
                    })
                }
            }
        } ?: run {
            binding.genderMovieRecyclerView.visibility = View.GONE
        }
    }

    private fun initAdapterCompany(it: List<ProductionCompany>) {
        it.takeIf { !it.isNullOrEmpty() }?.let {
            binding.companyRecyclerView.apply {
                adapter = CompanyAdapter(mutableList = it).apply {
                    setOnItemClickListener(object : CompanyAdapter.ClickListener {
                        override fun onItemClickListener(company: ProductionCompany) {

                        }
                    })
                }
            }
        } ?: run {
            binding.companyRecyclerView.visibility = View.GONE
        }
    }

    private fun initMovieDetails(it: MovieDetailsResponse) {
        binding.storylineMovie.apply {
            it.overview.takeIf { !it.isNullOrEmpty() }?.let { text = it } ?: run {
                binding.storyline.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.sloganMovie.apply {
            it.slogan.takeIf { !it.isNullOrEmpty() }?.let { text = it } ?: run {
                binding.sloganLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.durationMovie.apply {
            it.runtime.takeIf { it != null }?.let { text = it.formatDuration() } ?: run {
                visibility = View.GONE
            }
        }
        binding.productionCountryMovie.apply {
            it.countries.takeIf { it.isNotEmpty() }?.let { text = it.formatCountry() } ?: run {
                binding.productionCountryLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.productionCompanyMovie.apply {
            it.companies.takeIf { it.isNotEmpty() }?.let { text = it.formatCompany() } ?: run {
                binding.productionCompanyLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
    }

    private fun ReviewResponse.initMovieReviews(): MutableList<Review> {
        val mutableList: MutableList<Review> = mutableListOf()
        results.forEachIndexed { index, review ->
            when (index < 3) {
                true -> {
                    mutableList.add(
                        element = review
                    )
                }
                else -> {
                    /** Do nothing **/
                }
            }
        }
        binding.reviewsMovie.text = String.format(
            format = getString(R.string.movie_reviews),
            args = arrayOf(totalResults)
        )
        return mutableList
    }

    private fun ViewPager2.initAutomaticSlide(it: List<Image>) {
        val timerTask = object : TimerTask() {
            override fun run() {
                this@initAutomaticSlide.post {
                    this@initAutomaticSlide.currentItem = (
                        this@initAutomaticSlide.currentItem + 1
                    ) % it.size
                }
            }
        }; stopBanner()
        timer = Timer().apply {
            schedule(
                timerTask, 2000, 3000
            )
        }
    }

    private fun stopBanner() {
        timer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
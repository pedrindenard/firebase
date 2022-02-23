package com.pdm.firebase.feature.presentation.fragment.details.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentMovieBinding
import com.pdm.firebase.feature.domain.model.credit.Cast
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.image.Image
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCompany
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderFlatRate
import com.pdm.firebase.feature.domain.model.review.Review
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
            initMovieDetails(id = movieId!!)
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

    private fun initMovieDetails(id: Int) {
        viewModel.getMovieRecommendations(id = id)
        viewModel.getMovieProviders(id = id)
        viewModel.getMovieDetails(id = id)
        viewModel.getMovieCredits(id = id)
        viewModel.getMovieSimilar(id = id)
        viewModel.getMovieReviews(id = id)
        viewModel.getMovieVideos(id = id)
        viewModel.getMovieImages(id = id)
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
            binding.storylineMovie.text = it.overview
            binding.sloganMovie.text = it.slogan
            binding.typeMovie.text = it.genres.formatGenres()
            binding.yearMovie.text = it.releaseDate.formatDate()
            binding.durationMovie.text = it.runtime?.formatDuration()
            binding.productionCountryMovie.text = it.countries.formatCountry()
            binding.productionCompanyMovie.text = it.companies.formatCompany()

            binding.companyRecyclerView.apply {
                adapter = CompanyAdapter(it.companies).apply {
                    setOnItemClickListener(object : CompanyAdapter.ClickListener {
                        override fun onItemClickListener(company: ProductionCompany) {

                        }
                    })
                }
            }

            binding.genderMovieRecyclerView.apply {
                adapter = GenderAdapter(it.genres).apply {
                    setOnItemClickListener(object : GenderAdapter.ClickListener {
                        override fun onItemClickListener(gender: Gender) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieCredits.observe(viewLifecycleOwner, {
            binding.crewMovie.text = it.crew.formatCrew()
            binding.actorsRecyclerView.apply {
                adapter = CastAdapter(it.cast).apply {
                    setOnItemClickListener(object : CastAdapter.ClickListener {
                        override fun onItemClickListener(people: Cast) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieRecommendations.observe(viewLifecycleOwner, {
            binding.moreLikeThisRecyclerView.apply {
                adapter = MovieAdapter(it.results).apply {
                    setOnItemClickListener(object : MovieAdapter.ClickListener {
                        override fun onItemClickListener(movie: Movie) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieProviders.observe(viewLifecycleOwner, {
            binding.providersRecyclerView.apply {
                adapter = ProviderAdapter(it.results.formatToList()).apply {
                    setOnItemClickListener(object : ProviderAdapter.ClickListener {
                        override fun onItemClickListener(provider: ProviderFlatRate) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieVideos.observe(viewLifecycleOwner, {
            binding.videosRecyclerView.apply {
                adapter = VideoAdapter(it.results).apply {
                    setOnItemClickListener(object : VideoAdapter.ClickListener {
                        override fun onItemClickListener(video: Video) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieSimilar.observe(viewLifecycleOwner, {
            binding.seeTooRecyclerView.apply {
                adapter = SimilarAdapter().apply {
                    updateAdapter(it.results)
                    setOnItemClickListener(object : SimilarAdapter.ClickListener {
                        override fun onItemClickListener(movie: Movie) {

                        }
                    })
                }
            }
        })

        viewModel.getMovieReviews.observe(viewLifecycleOwner, {
            val mutableList: MutableList<Review> = mutableListOf()
            it.results.forEachIndexed { index, review ->
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
            binding.reviewsRecyclerView.apply {
                adapter = ReviewsAdapter().apply {
                    updateAdapter(mutableList)
                    setOnItemClickListener(object : ReviewsAdapter.ClickListener {
                        override fun onItemClickListener(review: Review) {

                        }
                    })
                }
            }
            binding.reviewsMovie.text = String.format(
                format = getString(R.string.movie_reviews),
                args = arrayOf(it.totalResults)
            )
        })

        viewModel.getMovieImages.observe(viewLifecycleOwner, {
            binding.logoMovie.apply {
                initAutomaticSlide(it = it.backdrops)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                adapter = ImageAdapter(it.backdrops).apply {
                    setOnItemClickListener(object : ImageAdapter.ClickListener {
                        override fun onItemClickListener(image: Image) {

                        }
                    })
                }
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
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
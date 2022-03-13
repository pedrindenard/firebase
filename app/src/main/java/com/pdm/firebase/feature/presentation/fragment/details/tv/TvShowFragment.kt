package com.pdm.firebase.feature.presentation.fragment.details.tv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentTvBinding
import com.pdm.firebase.feature.domain.model.credit.movie.Credit
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.image.Image
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCompany
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderFlatRate
import com.pdm.firebase.feature.domain.model.review.Review
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.tv.TvShow
import com.pdm.firebase.feature.domain.model.tv.details.LastEpisodeToAir
import com.pdm.firebase.feature.domain.model.tv.details.Season
import com.pdm.firebase.feature.domain.model.tv.details.TvDetailsResponse
import com.pdm.firebase.feature.domain.model.video.Video
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.details.movie.adapter.*
import com.pdm.firebase.feature.presentation.fragment.details.tv.adapter.SeasonAdapter
import com.pdm.firebase.feature.presentation.fragment.details.tv.adapter.SimilarAdapter
import com.pdm.firebase.feature.presentation.fragment.details.tv.viewmodel.TvShowViewModel
import com.pdm.firebase.feature.presentation.fragment.home.adapter.TvAdapter
import com.pdm.firebase.feature.presentation.fragment.review.adapter.ReviewsAdapter
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class TvShowFragment : BaseFragment() {

    private val viewModel by viewModel<TvShowViewModel>()
    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!
    private var tvId: Int? = null
    private var timer: Timer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            tvId = it.getSerializable(ARGS) as Int
            viewModel.initViewModel(
                id = tvId!!
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initActions()
    }

    private fun initActions() {
        binding.rateTv.setOnSingleClickListener {

        }

        binding.shareTv.setOnSingleClickListener {

        }

        binding.favTv.setOnSingleClickListener {

        }

        binding.crewLabel.setOnSingleClickListener {

        }

        binding.seeMoreTv.setOnSingleClickListener {

        }

        binding.reviewsMoreTv.setOnSingleClickListener {

        }
    }

    private fun initObservers() {
        viewModel.getTvDetails.observe(viewLifecycleOwner, {
            binding.titleTv.text = it.name
            binding.tvRateStar.rating = it.voteAverage.toFloat()
            binding.originalTitleTv.text = it.originalName
            binding.averageTv.text = it.voteAverage.toString()
            binding.votesTv.text = it.voteCount.toString()
            binding.premiereTv.text = it.firstAirDate
            binding.genderTv.text = it.genres.formatGenres()
            binding.yearTv.text = it.firstAirDate.formatDate()
            binding.runtimeTv.text = it.runtime.formatRuntime()
            binding.seasonsAdapter.initViewPagerSeason(it.seasons)
            initLastEpisode(it.lastEpisodeToAir)
            initNextEpisode(it.nextEpisodeToAir)
            initAdapterCompany(it.companies)
            initAdapterSeasons(it.seasons)
            initAdapterGender(it.genres)
            initMovieDetails(it)
        })

        viewModel.getTvCredits.observe(viewLifecycleOwner, {
            binding.crewTv.apply {
                it.crew.takeIf { it.isNotEmpty() }?.let { text = it.formatCrew() } ?: run {
                    binding.crewLabel.visibility = View.GONE
                    visibility = View.GONE
                }
            }
            binding.actorsRecyclerView.apply {
                adapter = CastAdapter(it.cast).apply {
                    setOnItemClickListener(object : CastAdapter.ClickListener {
                        override fun onItemClickListener(people: Credit) {

                        }
                    })
                }
            }
        })

        viewModel.getTvRecommendations.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.moreLikeThisRecyclerView.apply {
                    adapter = TvAdapter(it).apply {
                        setOnItemClickListener(object : TvAdapter.ClickListener {
                            override fun onItemClickListener(tvShow: TvShow) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.moreLikeThisConstraint.visibility = View.GONE
            }
        })

        viewModel.getTvProviders.observe(viewLifecycleOwner, { it ->
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

        viewModel.getTvVideos.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.videosRecyclerView.apply {
                    adapter = VideoAdapter(it).apply {
                        setOnItemClickListener(object : VideoAdapter.ClickListener {
                            override fun onItemClickListener(video: Video) {
                                activity?.startVideoActivity(
                                    key = video.key
                                )
                            }
                        })
                    }
                }
            } ?: run {
                binding.videosConstraint.visibility = View.GONE
            }
        })

        viewModel.getTvSimilar.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.seeTooRecyclerView.apply {
                    adapter = SimilarAdapter().apply {
                        updateAdapter(mutableList = it)
                        setOnItemClickListener(object : SimilarAdapter.ClickListener {
                            override fun onItemClickListener(tv: TvShow) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.seeTooConstraint.visibility = View.GONE
            }
        })

        viewModel.getTvReviews.observe(viewLifecycleOwner, { it ->
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

        viewModel.getTvImages.observe(viewLifecycleOwner, { it ->
            it.backdrops.takeIf { !it.isNullOrEmpty() }?.let {
                binding.logoTv.apply {
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

        viewModel.onSuccess.observe(viewLifecycleOwner, {

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
            binding.genderTvRecyclerView.apply {
                adapter = GenderAdapter(mutableList = it).apply {
                    setOnItemClickListener(object : GenderAdapter.ClickListener {
                        override fun onItemClickListener(gender: Gender) {

                        }
                    })
                }
            }
        } ?: run {
            binding.genderTvRecyclerView.visibility = View.GONE
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

    private fun initAdapterSeasons(it: List<Season>) {
        it.takeIf { !it.isNullOrEmpty() }?.let {
            binding.seasonsAdapter.apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                adapter = SeasonAdapter(mutableList = it).apply {
                    setOnItemClickListener(object : SeasonAdapter.ClickListener {
                        override fun onItemClickListener(season: Season) {

                        }
                    })
                }
            }
        } ?: run {
            binding.infoTvConstraint.visibility = View.GONE
        }
    }

    private fun initLastEpisode(it: LastEpisodeToAir?) {
        it.takeIf { it != null }?.let {
            binding.lastEpName.text = it.name
            binding.lastEpDate.text = it.airDate
            binding.lastEpOverView.text = it.overview
            binding.lastEpRating.text = it.voteAverage.toString()
            binding.lastEpSeasonAndEpNumber.text = String.format(
                getString(R.string.last_season_ep),
                it.seasonNumber,
                it.episodeNumber
            )
            binding.lastEpImage.loadImage(
                requireView(),
                it.stillPath,
                TMDB_SMALL_IMAGE
            )
        } ?: run {
            binding.lastEpToAirConstraint.visibility = View.GONE
        }
    }

    private fun initNextEpisode(it: LastEpisodeToAir?) {
        it.takeIf { it != null }?.let {
            binding.nextEpName.text = it.name
            binding.nextEpDate.text = it.airDate
            binding.nextEpOverView.text = it.overview
            binding.nextEpRating.text = it.voteAverage.toString()
            binding.nextEpSeasonAndEpNumber.text = String.format(
                getString(R.string.last_season_ep),
                it.seasonNumber,
                it.episodeNumber
            )
            binding.nextEpImage.loadImage(
                requireView(),
                it.stillPath,
                TMDB_SMALL_IMAGE
            )
        } ?: run {
            binding.nextEpToAirConstraint.visibility = View.GONE
        }
    }

    private fun initMovieDetails(it: TvDetailsResponse) {
        binding.createdBy.apply {
            it.createdBy.takeIf { !it.isNullOrEmpty() }?.let { text = it.formatCreators() } ?: run {
                binding.createdByLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.storylineTv.apply {
            it.overview.takeIf { !it.isNullOrEmpty() }?.let { text = it } ?: run {
                binding.storyline.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.sloganTv.apply {
            it.tagline.takeIf { !it.isNullOrEmpty() }?.let { text = it } ?: run {
                binding.sloganLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.productionCountryTv.apply {
            it.countries.takeIf { it.isNotEmpty() }?.let { text = it.formatCountry() } ?: run {
                binding.productionCountryLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.productionCompanyTv.apply {
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
        binding.reviewsTv.text = String.format(
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

    private fun ViewPager2.initViewPagerSeason(seasons: List<Any>) {
        if (seasons.size != 1) {
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            binding.previousSeason.handlerStates(isEnable = false)
                            binding.nextSeason.handlerStates(isEnable = true)
                        }
                        seasons.size - 1 -> {
                            binding.nextSeason.handlerStates(isEnable = false)
                            binding.previousSeason.handlerStates(isEnable = true)
                        }
                        else -> {
                            binding.previousSeason.handlerStates(isEnable = true)
                            binding.nextSeason.handlerStates(isEnable = true)
                        }
                    }
                }
            })
        } else {
            binding.previousSeason.handlerStates(isEnable = false)
            binding.nextSeason.handlerStates(isEnable = false)
        }

        binding.seasonNumberLabel.text = String.format(
            getString(R.string.seasons), seasons.size.toString()
        )

        binding.nextSeason.setOnClickListener {
            this.takeIf {
                it.currentItem < seasons.size
            }?.let {
                this.currentItem += 1
            }
        }

        binding.previousSeason.setOnClickListener {
            this.takeIf {
                it.currentItem < seasons.size
            }?.let {
                this.currentItem -= 1
            }
        }
    }

    private fun AppCompatButton.handlerStates(isEnable: Boolean) {
        setTextColor(
            ContextCompat.getColor(
                requireContext(), when (isEnable) {
                    true -> {
                        isEnabled = true
                        R.color.white
                    }
                    false -> {
                        isEnabled = false
                        R.color.boulder
                    }
                }
            )
        )
    }

    private fun stopBanner() {
        timer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
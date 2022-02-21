package com.pdm.firebase.feature.presentation.fragment.search.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdm.firebase.databinding.DialogFilterBinding
import com.pdm.firebase.feature.domain.model.filter.Filter
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.filter.FilterResponse
import com.pdm.firebase.feature.domain.model.movie.MovieYears
import com.pdm.firebase.feature.domain.model.region.Region
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowYears
import com.pdm.firebase.feature.presentation.base.BaseDialogFragment
import com.pdm.firebase.feature.presentation.fragment.search.adapter.FilterAdapter
import com.pdm.firebase.feature.presentation.fragment.search.adapter.RegionAdapter
import com.pdm.firebase.util.setOnSingleClickListener

class FilterDialog(
    private val regionList: RegionResponse,
    private val filter: FilterCreated?
) : BaseDialogFragment() {

    private lateinit var mClickListener: ClickListener
    private lateinit var filterList: List<Filter>

    private var regionSelected: String = "AE"
    private var categorySelected: String = "1"

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filterList = FilterResponse.available
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilterLoading()
        //initAdapterRegion()
        initAdapterFilter()
        initActions()
    }

    private fun initActions() {
        binding.filterButton.setOnSingleClickListener {
            val regionIsEnable = binding.enableRegion.isChecked
            val adultContent = binding.enableAdultContent.isChecked
            val releaseIsEnable = binding.enableReleaseDate.isChecked
            val releaseDates = binding.yearSlider.value

            mClickListener.onClickListener(
                filters = FilterCreated(
                    releaseDate = if (releaseIsEnable) releaseDates else null,
                    region = if (regionIsEnable) regionSelected else null,
                    category = categorySelected.toInt(),
                    adultContent = adultContent,
                )
            )
        }

        binding.clearButton.setOnSingleClickListener {
            mClickListener.onClickListener(
                filters = null
            )
        }
    }

    private fun initAdapterFilter() {
        binding.recyclerViewFilter.apply {
            adapter = FilterAdapter(filterList).apply {
                setOnItemClickListener(object : FilterAdapter.ClickListener {
                    override fun onItemClickListener(filter: Filter) {
                        when (filter.param) {
                            5 -> {
                                selectSliderYear(it = TvShowYears.getTvShowsYears())
                            }
                            else -> {
                                selectSliderYear(it = MovieYears.getMoviesYears())
                            }
                        }
                        categorySelected = filter.param.toString()
                    }
                })
            }
        }
    }

    private fun initAdapterRegion() {
        binding.recyclerViewRegion.apply {
            adapter = RegionAdapter(regionList.results).apply {
                setOnItemClickListener(object : RegionAdapter.ClickListener {
                    override fun onItemClickListener(region: Region) {
                        regionSelected = region.initials
                    }
                })
            }
        }
    }

    private fun initFilterLoading() {
        this.filter?.let {
            categorySelected = filter.category.toString()

            filter.releaseDate.takeIf { it != null }?.let {
                initSliderYear(it = filter.releaseDate!!)
                binding.enableReleaseDate.isChecked = true
            } ?: kotlin.run { selectSliderYear(it = MovieYears.getMoviesYears()) }

            filter.region.takeIf { it != null }?.let {
                regionList.results.forEach { it.isSelect = it.initials == filter.region }
                binding.enableRegion.isChecked = true
                regionSelected = filter.region!!
            } ?: kotlin.run {
                regionList.results.forEach { it.isSelect = false }
                regionList.results.first().isSelect = true
            }

            filter.adultContent.takeIf { it != null }?.let {
                binding.enableAdultContent.isChecked = filter.adultContent!!
            }
        } ?: kotlin.run { selectSliderYear(it = MovieYears.getMoviesYears())
            regionList.results.forEach { it.isSelect = false }
            regionList.results.first().isSelect = true
        }
    }

    private fun selectSliderYear(it: Pair<Float, Float>) {
        binding.yearSlider.apply {
            value = it.first
            valueFrom = it.first
            valueTo = it.second
        }
    }

    private fun initSliderYear(it: Float) {
        val pair = MovieYears.getMoviesYears()
        binding.yearSlider.apply {
            value = it
            valueFrom = pair.first
            valueTo = pair.second
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClickListener(filters: FilterCreated?)
    }
}
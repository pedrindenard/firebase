package com.pdm.firebase.feature.presentation.fragment.search

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.arquitecture.Event.Companion.mapTo
import com.pdm.firebase.arquitecture.Event.Companion.mapToMutableList
import com.pdm.firebase.databinding.FragmentSearchBinding
import com.pdm.firebase.feature.domain.enums.SearchType
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.search.adapter.*
import com.pdm.firebase.feature.presentation.fragment.search.dialog.FilterDialog
import com.pdm.firebase.feature.presentation.fragment.search.viewmodel.SearchViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    private lateinit var searchAdapter: SearchAdapter

    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var scrollListener: RecyclerView.OnScrollListener? = null
    private var regionResponse: RegionResponse? = null
    private var filter: FilterCreated? = null
    private var timer: CountDownTimer? = null
    private var query: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getRegions()
        viewModel.searchMulti(
            filter = filter,
            query = "a".also {
                query = it
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchInput.defaultStateColor()
        initAdapterSearch()
        initObservers()
        initSearch()
    }

    private fun initSearch() {
        binding.searchField.addListenerSearch(listener = false,
            beforeTextChanged = { handlerProgressBar(isVisible = true) },
            onTextChanged = { viewModel.setFilters() },
            afterTextChanged = {
                if (it?.length!! != 0) {
                    handlerScrollListener()
                    handlerSearch(
                        param = filter?.category,
                        it = it.toString()
                    )
                } else {
                    handlerScrollListener()
                    handlerSearch(
                        param = filter?.category
                    )
                }
            }
        )

        binding.filter.setOnSingleClickListener {
            regionResponse?.let {
                setDialogFilter(response = it, filters = filter)
            }
        }
    }

    private fun initObservers() {
        viewModel.getSearchMulti.observe(viewLifecycleOwner, {
            it?.let {
                searchAdapter.updateAdapter(mutableList = it.results)
                setScrollListener(it = it.mapTo(), param = 1)
                handlerProgressBar(isVisible = false)
            }
        })

        viewModel.getSearchCollections.observe(viewLifecycleOwner, {
            it?.let {
                searchAdapter.updateAdapter(mutableList = it.results.mapToMutableList())
                setScrollListener(it = it.mapTo(), param = 2)
                handlerProgressBar(isVisible = false)
            }
        })

        viewModel.getSearchActors.observe(viewLifecycleOwner, {
            it?.let {
                searchAdapter.updateAdapter(mutableList = it.results.mapToMutableList())
                setScrollListener(it = it.mapTo(), param = 3)
                handlerProgressBar(isVisible = false)
            }
        })

        viewModel.getSearchMovies.observe(viewLifecycleOwner, {
            it?.let {
                searchAdapter.updateAdapter(mutableList = it.results.mapToMutableList())
                setScrollListener(it = it.mapTo(), param = 4)
                handlerProgressBar(isVisible = false)
            }
        })

        viewModel.getSearchTvShows.observe(viewLifecycleOwner, {
            it?.let {
                searchAdapter.updateAdapter(mutableList = it.results.mapToMutableList())
                setScrollListener(it = it.mapTo(), param = 5)
                handlerProgressBar(isVisible = false)
            }
        })

        viewModel.getRegions.observe(viewLifecycleOwner, {
            it?.let { regionResponse = it }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun initAdapterSearch() {
        binding.recyclerViewSearch.apply {
            searchAdapter = SearchAdapter().apply {
                setOnItemClickListener(object : SearchAdapter.ClickListener {
                    override fun onItemClickListener(search: Search) {

                    }
                })
                setHasStableIds(true)
                adapter = this
            }
            startIntroAnimation()
        }
    }

    private fun setScrollListener(it: SearchResponse, param: Int) {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemVisible = visibleItemCount + pastVisibleItems
                val totalItemCount = layoutManager.itemCount
                val isLastPage = it.currentPage != it.totalPage

                if ((totalItemVisible >= totalItemCount) && isLastPage) {
                    handlerProgressBar(isVisible = true)
                    handlerScrollListener()
                    handlerSearch(
                        param = param,
                        it = query
                    )
                }
            }
        }
        binding.recyclerViewSearch.addOnScrollListener(
            scrollListener!!
        )
    }

    private fun setDialogFilter(response: RegionResponse, filters: FilterCreated?) {
        activity?.let {
            FilterDialog(response, filters).apply {
                show(it.supportFragmentManager, "")
                setOnItemClickListener(object : FilterDialog.ClickListener {
                    override fun onClickListener(filters: FilterCreated?) {
                        handlerScrollListener(); viewModel.setFilters()
                        handlerProgressBar(isVisible = true); filter = filters
                        handlerSearch(param = filter?.category)
                        dismiss(); binding.searchField.text?.clear()
                    }
                })
            }
        }
    }

    private fun handlerSearch(param: Int?, it: String? = "a") {
        when (param) {
            SearchType.SEARCH_COLLECTION.value -> {
                handleTimer {
                    viewModel.searchCollection(
                        query = it!!.also {
                            query = it
                        }
                    )
                }
            }
            SearchType.SEARCH_PEOPLE.value -> {
                handleTimer {
                    viewModel.searchPeople(
                        query = it!!.also {
                            query = it
                        },
                        filter = filter
                    )
                }
            }
            SearchType.SEARCH_MOVIE.value -> {
                handleTimer {
                    viewModel.searchMovie(
                        query = it!!.also {
                            query = it
                        },
                        filter = filter
                    )
                }
            }
            SearchType.SEARCH_TV.value -> {
                handleTimer {
                    viewModel.searchTvShow(
                        query = it!!.also {
                            query = it
                        },
                        filter = filter
                    )
                }
            }
            else -> {
                handleTimer {
                    viewModel.searchMulti(
                        query = it!!.also {
                            query = it
                        },
                        filter = filter
                    )
                }
            }
        }
    }

    private fun handleTimer(search: () -> Unit) {
        timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                search(); cancel()
            }
        }.start()
    }

    private fun handlerScrollListener() {
        if (scrollListener != null) {
            binding.recyclerViewSearch.removeOnScrollListener(
                scrollListener!!
            )
        }
    }

    private fun handlerProgressBar(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) {
            timer?.cancel(); View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
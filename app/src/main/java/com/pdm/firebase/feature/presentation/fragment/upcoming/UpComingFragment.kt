package com.pdm.firebase.feature.presentation.fragment.upcoming

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentUpcomingBinding
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.upcoming.adapter.UpComingAdapter
import com.pdm.firebase.feature.presentation.fragment.upcoming.viewmodel.UpComingViewModel
import com.pdm.firebase.util.ARGS
import com.pdm.firebase.util.RED
import com.pdm.firebase.util.startIntroAnimation
import com.pdm.firebase.util.startVideoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpComingFragment: BaseFragment() {

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var upComingAdapter: UpComingAdapter

    private val viewModel by viewModel<UpComingViewModel>()
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private var onVideoStartup: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getUpcomingMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initAdapter()
    }

    private fun initObservers() {
        viewModel.getUpcomingMovie.observe(viewLifecycleOwner, {
            upComingAdapter.updateAdapter(mutableList = it.results)
            setScrollListener(notIsLastPage = it.totalPage != it.currentPage)
            handlerProgressBar(isVisible = false)
        })

        viewModel.getMovieVideo.observe(viewLifecycleOwner, { it ->
            if (onVideoStartup) {
                it.results.takeIf { it.isNotEmpty() }?.run {
                    forEach {
                        if (it.type == "Trailer" && it.official) {
                            activity?.startVideoActivity(
                                key = it.key
                            )
                            return@run
                        }
                    }
                    forEach {
                        if (it.type == "Trailer") {
                            activity?.startVideoActivity(
                                key = it.key
                            )
                            return@run
                        }
                    }
                    activity?.startVideoActivity(
                        key = first().key
                    )
                } ?: run {
                    showSnackBar(
                        description = getString(R.string.trailer_not_found),
                        color = RED
                    )
                }
            } else onVideoStartup = true
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun initAdapter() {
        binding.upComingRecyclerView.apply {
            upComingAdapter = UpComingAdapter().apply {
                setOnItemClickListener(object : UpComingAdapter.ClickListener {
                    override fun onDetailsClickListener(movie: Movie) {
                        findNavController().navigate(
                            R.id.movieFragment, Bundle().apply {
                                putSerializable(ARGS, movie.id)
                            }, NavOptions.Builder().apply {
                                setEnterAnim(R.anim.fade_in)
                                setExitAnim(R.anim.fade_out)
                                setPopEnterAnim(R.anim.fade_in)
                                setPopExitAnim(R.anim.fade_out)
                            }.build()
                        )
                    }

                    override fun onPlayClickListener(movie: Movie) {
                        viewModel.getVideoMovie(id = movie.id)
                        showProgressDialog()
                    }
                })
                setHasStableIds(true)
                adapter = this
            }
            startIntroAnimation()
        }
    }

    private fun setScrollListener(notIsLastPage: Boolean) {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemVisible = visibleItemCount + pastVisibleItems
                val totalItemCount = layoutManager.itemCount

                if ((totalItemVisible >= totalItemCount) && notIsLastPage) {
                    handlerScrollListener()
                    handlerProgressBar(isVisible = true)
                    viewModel.getUpcomingMovie()
                }
            }
        }
        binding.upComingRecyclerView.addOnScrollListener(
            scrollListener
        )
    }

    private fun handlerScrollListener() {
        binding.upComingRecyclerView.removeOnScrollListener(
            scrollListener
        )
    }

    private fun handlerProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    private fun onViewDestroy() {
        onVideoStartup = false
       _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onViewDestroy()
    }
}
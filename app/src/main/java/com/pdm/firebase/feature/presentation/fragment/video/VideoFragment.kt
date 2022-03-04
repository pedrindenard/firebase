package com.pdm.firebase.feature.presentation.fragment.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentVideoBinding
import com.pdm.firebase.feature.domain.enums.GenreType
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.video.adapter.VideoAdapter
import com.pdm.firebase.feature.presentation.fragment.video.viewmodel.VideoViewModel
import com.pdm.firebase.util.ARGS
import com.pdm.firebase.util.RED
import com.pdm.firebase.util.initVideo
import com.pdm.firebase.util.startIntroAnimation
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment : BaseFragment() {

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var videoAdapter: VideoAdapter

    private var onVideoStartup: Boolean = true
    private val viewModel by viewModel<VideoViewModel>()
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private var param: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            param = it.getSerializable(ARGS) as String
            when (param) {
                GenreType.TV.value -> {
                    viewModel.getTvOnAir()
                }
                GenreType.MOVIE.value -> {
                    viewModel.getMovieOnAir()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initAdapter()
    }

    private fun initObservers() {
        viewModel.getSearchResponse.observe(viewLifecycleOwner, {
            videoAdapter.updateAdapter(mutableList = it.results)
            setScrollListener(notIsLastPage = it.totalPage != it.currentPage)
            handlerProgressBar(isVisible = false)
        })

        viewModel.getVideoResponse.observe(viewLifecycleOwner, {
            if (onVideoStartup) {
                activity?.initVideo(it) {
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
        binding.recyclerView.apply {
            videoAdapter = VideoAdapter().apply {
                setOnItemClickListener(object : VideoAdapter.ClickListener {
                    override fun onDetailsClickListener(search: Search) {
                        when (param) {
                            GenreType.TV.value -> {
                                initDetails(
                                    id = search.id
                                )
                            }
                            GenreType.MOVIE.value -> {
                                initDetails(
                                    id = search.id
                                )
                            }
                        }
                    }

                    override fun onPlayClickListener(search: Search) {
                        when (param) {
                            GenreType.TV.value -> {
                                viewModel.getTvVideo(
                                    id = search.id
                                )
                            }
                            GenreType.MOVIE.value -> {
                                viewModel.getMovieVideo(
                                    id = search.id
                                )
                            }
                        }
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
                    handlerProgressBar(isVisible = true)
                    handlerScrollListener()
                    when (param) {
                        GenreType.TV.value -> {
                            viewModel.getTvOnAir()
                        }
                        GenreType.MOVIE.value -> {
                            viewModel.getMovieOnAir()
                        }
                    }
                }
            }
        }
        binding.recyclerView.addOnScrollListener(
            scrollListener
        )
    }

    private fun handlerScrollListener() {
        binding.recyclerView.removeOnScrollListener(
            scrollListener
        )
    }

    private fun handlerProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    private fun initDetails(id: Int) {
        findNavController().navigate(
            R.id.movieFragment, Bundle().apply {
                putSerializable(ARGS, id)
            }, NavOptions.Builder().apply {
                setEnterAnim(R.anim.fade_in)
                setExitAnim(R.anim.fade_out)
                setPopEnterAnim(R.anim.fade_in)
                setPopExitAnim(R.anim.fade_out)
            }.build()
        )
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
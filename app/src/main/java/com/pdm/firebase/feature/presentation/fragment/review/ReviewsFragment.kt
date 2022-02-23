package com.pdm.firebase.feature.presentation.fragment.review

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.databinding.FragmentReviewsBinding
import com.pdm.firebase.feature.domain.model.review.Review
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.details.movie.viewmodel.MovieViewModel
import com.pdm.firebase.feature.presentation.fragment.review.adapter.ReviewsAdapter
import com.pdm.firebase.util.ARGS
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewsFragment : BaseFragment() {

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var reviewAdapter: ReviewsAdapter

    private val viewModel by viewModel<MovieViewModel>()
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private var paramId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            paramId = it.getSerializable(ARGS) as Int
            viewModel.getMovieReviews(
                id = paramId!!
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initAdapter()
    }

    private fun initObservers() {
        viewModel.getMovieReviews.observe(viewLifecycleOwner, {
            reviewAdapter.updateAdapter(mutableList = it.results)
            setScrollListener(notIsLastPage = it.totalPage != it.currentPage)
            handlerProgressBar(isVisible = false)
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun initAdapter() {
        binding.reviewsRecyclerView.apply {
            reviewAdapter = ReviewsAdapter().apply {
                setOnItemClickListener(object : ReviewsAdapter.ClickListener {
                    override fun onItemClickListener(review: Review) {

                    }
                })
                adapter = this
            }
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
                    viewModel.getMovieReviews(
                        id = paramId!!
                    )
                }
            }
        }
        binding.reviewsRecyclerView.addOnScrollListener(
            scrollListener
        )
    }

    private fun handlerScrollListener() {
        binding.reviewsRecyclerView.removeOnScrollListener(
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
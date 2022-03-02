package com.pdm.firebase.feature.presentation.fragment.upcoming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.usecase.MovieDetailsUseCase
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UpComingViewModel(
    private val movieUseCase: MovieUseCase,
    private val detailsUseCase: MovieDetailsUseCase,
) : BaseViewModel() {

    private val _getUpcomingMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getUpcomingMovie = _getUpcomingMovie as LiveData<MovieResponse>

    private val _getMovieVideo: MutableLiveData<VideoResponse> = MutableLiveData()
    val getMovieVideo = _getMovieVideo as LiveData<VideoResponse>

    private var upComingMovieOld: MovieResponse? = null
    private var pagging: Int = 1

    fun getUpcomingMovie() {
        viewModelScope.launch {
            when (
                val response = movieUseCase.getUpcomingMovie.invoke(
                    page = pagging,
                    ignoreCache = true
                )
            ) {
                is Resource.Success -> {
                    response.data!!.let {
                        upComingMovieOld?.apply {
                            results.addAll(it.results)
                            currentPage = pagging
                        } ?: run { upComingMovieOld = it }
                        _getUpcomingMovie.postValue(upComingMovieOld ?: it)
                    }; pagging++
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun getVideoMovie(id: Int) {
        viewModelScope.launch {
            when (val response = detailsUseCase.getMovieVideos.invoke(id = id)) {
                is Resource.Success -> {
                    _getMovieVideo.postValue(response.data!!)
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }
}
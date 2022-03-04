package com.pdm.firebase.feature.presentation.fragment.video.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Event.Companion.mapTo
import com.pdm.firebase.arquitecture.Event.Companion.mapToMutableList
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.usecase.MovieDetailsUseCase
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.TvShowUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class VideoViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvUserCase: TvShowUseCase,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val tvDetailsUserCase: MovieDetailsUseCase
) : BaseViewModel() {

    private val _getSearchResponse: MutableLiveData<SearchResponse> = MutableLiveData()
    val getSearchResponse = _getSearchResponse as LiveData<SearchResponse>

    private val _getVideoResponse: MutableLiveData<VideoResponse> = MutableLiveData()
    val getVideoResponse = _getVideoResponse as LiveData<VideoResponse>

    private var searchResponseOld: SearchResponse? = null
    private var pagging: Int = 1

    fun getTvOnAir() {
        viewModelScope.launch {
            when (
                val response = tvUserCase.getTvShowOnAir.invoke(
                    page = pagging, ignoreCache = true
                )
            ) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchResponseOld?.apply {
                            results.addAll(it.results.mapToMutableList())
                            currentPage = pagging
                        } ?: run { searchResponseOld = it.mapTo() }
                        _getSearchResponse.postValue(searchResponseOld ?: it.mapTo())
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

    fun getMovieOnAir() {
        viewModelScope.launch {
            when (
                val response = movieUseCase.getNowPlayingMovie.invoke(
                    page = pagging, ignoreCache = true
                )
            ) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchResponseOld?.apply {
                            results.addAll(it.results.mapToMutableList())
                            currentPage = pagging
                        } ?: run { searchResponseOld = it.mapTo() }
                        _getSearchResponse.postValue(searchResponseOld ?: it.mapTo())
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

    fun getMovieVideo(id: Int) {
        viewModelScope.launch {
            when (val response = movieDetailsUseCase.getMovieVideos.invoke(id = id)) {
                is Resource.Success -> {
                    _getVideoResponse.postValue(response.data!!)
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

    fun getTvVideo(id: Int) {
        viewModelScope.launch {
            when (val response = tvDetailsUserCase.getMovieVideos.invoke(id = id)) {
                is Resource.Success -> {
                    _getVideoResponse.postValue(response.data!!)
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
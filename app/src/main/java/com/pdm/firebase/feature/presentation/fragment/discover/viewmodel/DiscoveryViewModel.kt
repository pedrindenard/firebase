package com.pdm.firebase.feature.presentation.fragment.discover.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.usecase.DiscoverUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class DiscoveryViewModel(private val useCase: DiscoverUseCase) : BaseViewModel() {

    private val _getMovies: MutableLiveData<MovieResponse> = MutableLiveData()
    private var moviesOld: MovieResponse? = null
    val getMovies = _getMovies as LiveData<MovieResponse>

    private val _getTvShows: MutableLiveData<TvShowResponse> = MutableLiveData()
    private var tvShowsOld: TvShowResponse? = null
    val getTvShows = _getTvShows as LiveData<TvShowResponse>

    private var pagging = 1

    fun getMovies(id: Int, sort: String? = "popularity.desc") {
        viewModelScope.launch {
            when (val response = useCase.getMovieByQuery.invoke(id, sort!!, pagging)) {
                is Resource.Success -> {
                    response.data!!.let {
                        moviesOld?.apply {
                            results.addAll(it.results)
                            currentPage = pagging
                        } ?: run { moviesOld = it }
                        _getMovies.postValue(moviesOld ?: it)
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

    fun getTvShows(id: Int, sort: String? = "popularity.desc") {
        viewModelScope.launch {
            when (val response = useCase.getTvShowByQuery.invoke(id, sort!!, pagging)) {
                is Resource.Success -> {
                    response.data!!.let {
                        tvShowsOld?.apply {
                            results.addAll(it.results)
                            currentPage = pagging
                        } ?: run { tvShowsOld = it }
                        _getTvShows.postValue(tvShowsOld ?: it)
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
}
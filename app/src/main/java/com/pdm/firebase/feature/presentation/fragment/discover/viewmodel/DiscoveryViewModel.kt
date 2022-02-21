package com.pdm.firebase.feature.presentation.fragment.discover.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.filter.FilterSort
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.usecase.DiscoverUseCase
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.TvShowUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class DiscoveryViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvShowUseCase: TvShowUseCase,
    private val discoverUseCase: DiscoverUseCase
    ) : BaseViewModel() {

    private val _getMovies: MutableLiveData<MovieResponse> = MutableLiveData()
    private var moviesOld: MovieResponse? = null
    val getMovies = _getMovies as LiveData<MovieResponse>

    private val _getTvShows: MutableLiveData<TvShowResponse> = MutableLiveData()
    private var tvShowsOld: TvShowResponse? = null
    val getTvShows = _getTvShows as LiveData<TvShowResponse>

    private val _getGenresTv: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGenresTv = _getGenresTv as LiveData<GenderResponse>

    private val _getGenresMovie: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGenresMovie = _getGenresMovie as LiveData<GenderResponse>

    private val _getSortsAvailable: MutableLiveData<List<Pair<String, String>>> = MutableLiveData()
    val getSortsAvailable = _getSortsAvailable as LiveData<List<Pair<String, String>>>

    private var pagging = 1

    fun getMovies(id: Int, sort: String? = "popularity.desc") {
        viewModelScope.launch {
            when (val response = discoverUseCase.getMovieByQuery.invoke(id, sort!!, pagging)) {
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
            when (val response = discoverUseCase.getTvShowByQuery.invoke(id, sort!!, pagging)) {
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

    fun getGenresTv() {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getGendersTvShow.invoke()) {
                is Resource.Success -> {
                    _getGenresTv.postValue(response.data!!)
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

    fun getGenresMovies() {
        viewModelScope.launch {
            when (val response = movieUseCase.getGendersMovie.invoke()) {
                is Resource.Success -> {
                    _getGenresMovie.postValue(response.data!!)
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

    fun getSorts() {
        viewModelScope.launch {
            _getSortsAvailable.postValue(
                FilterSort.available
            )
        }
    }

    fun setFilters() {
        tvShowsOld = null
        moviesOld = null
        pagging = 1
    }
}
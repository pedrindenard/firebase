package com.pdm.firebase.feature.presentation.fragment.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.TvShowUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvShowUseCase: TvShowUseCase,
    private val cache: CacheImpl
) : BaseViewModel() {

    private val _getNowPlayingMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getNowPlayingMovie = _getNowPlayingMovie as LiveData<MovieResponse>

    private val _getPopularMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getPopularMovie = _getPopularMovie as LiveData<MovieResponse>

    private val _getTopRatedMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getTopRatedMovie = _getTopRatedMovie as LiveData<MovieResponse>

    private val _getGendersMovie: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGendersMovie = _getGendersMovie as LiveData<GenderResponse>

    private val _getMovieByGender: MutableLiveData<MovieResponse> = MutableLiveData()
    val getMovieByGender = _getMovieByGender as LiveData<MovieResponse>

    private val _getUpcomingMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getUpcomingMovie = _getUpcomingMovie as LiveData<MovieResponse>

    private val _getTvShowPopular: MutableLiveData<TvShowResponse> = MutableLiveData()
    val getTvShowPopular = _getTvShowPopular as LiveData<TvShowResponse>

    private val _getTvShowTopRated: MutableLiveData<TvShowResponse> = MutableLiveData()
    val getTvShowTopRated = _getTvShowTopRated as LiveData<TvShowResponse>

    private val _getTvShowOnAir: MutableLiveData<TvShowResponse> = MutableLiveData()
    val getTvShowOnAir = _getTvShowOnAir as LiveData<TvShowResponse>

    private val _getGendersTv: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGendersTv = _getGendersTv as LiveData<GenderResponse>

    private val _getTvShowByGender: MutableLiveData<TvShowResponse> = MutableLiveData()
    val getTvShowByGender = _getTvShowByGender as LiveData<TvShowResponse>

    private val _getBestActors: MutableLiveData<ActorsResponse> = MutableLiveData()
    val getBestActors = _getBestActors as LiveData<ActorsResponse>

    fun getNowPlayingMovie(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getNowPlayingMovie.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getNowPlayingMovie.postValue(response.data!!)
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

    fun getPopularMovie(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getPopularMovie.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getPopularMovie.postValue(response.data!!)
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

    fun getTopRatedMovie(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getRatedMovie.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getTopRatedMovie.postValue(response.data!!)
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

    fun getGendersMovie(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getGendersMovie.invoke(ignoreCache)) {
                is Resource.Success -> {
                    _getGendersMovie.postValue(response.data!!)
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

    fun getMovieByGender(id: Int, ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getMovieByGender.invoke(id = id, page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getMovieByGender.postValue(response.data!!)
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

    fun getUpcomingMovie(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getUpcomingMovie.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getUpcomingMovie.postValue(response.data!!)
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

    fun getGendersTvShow(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getGendersTvShow.invoke(ignoreCache)) {
                is Resource.Success -> {
                    _getGendersTv.postValue(response.data!!)
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

    fun getTvShowByGender(id: Int, ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getTvShowByGender.invoke(id = id ,page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getTvShowByGender.postValue(response.data!!)
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

    fun getTvShowPopular(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getTvShowPopular.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getTvShowPopular.postValue(response.data!!)
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

    fun getTvShowTopRated(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getTvShowTopRated.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getTvShowTopRated.postValue(response.data!!)
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

    fun getTvShowOnAir(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getTvShowOnAir.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getTvShowOnAir.postValue(response.data!!)
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

    fun getBestActors(ignoreCache: Boolean? = false) {
        viewModelScope.launch {
            when (val response = movieUseCase.getBestActors.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getBestActors.postValue(response.data!!)
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

    fun addInfoOnCache() {
        viewModelScope.launch {
            if (
                cache.get(
                    key = CacheImpl.DEFAULT
                ).isEmpty()
            ) {
                cache.insert(
                    key = CacheImpl.DEFAULT,
                    value = "User open the app"
                )
            }
        }
    }
}
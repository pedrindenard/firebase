package com.pdm.firebase.feature.presentation.fragment.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val useCase: MovieUseCase,
    private val cache: CacheImpl
) : BaseViewModel() {

    private val _getSuperBanner: MutableLiveData<MovieResponse> = MutableLiveData()
    val getSuperBanner = _getSuperBanner as LiveData<MovieResponse>

    private val _getPopularMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getPopularMovie = _getPopularMovie as LiveData<MovieResponse>

    private val _getRatedMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getRatedMovie = _getRatedMovie as LiveData<MovieResponse>

    private val _getGendersMovie: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGendersMovie = _getGendersMovie as LiveData<GenderResponse>

    private val _getMovieByGender: MutableLiveData<MovieResponse> = MutableLiveData()
    val getMovieByGender = _getMovieByGender as LiveData<MovieResponse>

    private val _getUpcomingMovie: MutableLiveData<MovieResponse> = MutableLiveData()
    val getUpcomingMovie = _getUpcomingMovie as LiveData<MovieResponse>

    private val _getBestActors: MutableLiveData<ActorsResponse> = MutableLiveData()
    val getBestActors = _getBestActors as LiveData<ActorsResponse>

    fun getSuperBanner(refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getSuperBanner.invoke(refresh)) {
                is Resource.Success -> {
                    _getSuperBanner.postValue(response.data!!)
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

    fun getPopularMovie(refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getPopularMovie.invoke(refresh)) {
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

    fun getRatedMovie(refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getRatedMovie.invoke(refresh)) {
                is Resource.Success -> {
                    _getRatedMovie.postValue(response.data!!)
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

    fun getGendersMovie(refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getGendersMovie.invoke(refresh)) {
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

    fun getMovieByGender(id: Int, refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getMovieByGender.invoke(id = id, refresh)) {
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

    fun getUpcomingMovie(refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getUpcomingMovie.invoke(refresh)) {
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

    fun getBestActors(refresh: Boolean? = false) {
        viewModelScope.launch {
            when (val response = useCase.getBestActors.invoke(refresh)) {
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
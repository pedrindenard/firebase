package com.pdm.firebase.feature.presentation.fragment.details.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.movie.MovieCreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.movie.details.MovieDetailsResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.usecase.MovieDetailsUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieViewModel(private val useCase: MovieDetailsUseCase) : BaseViewModel() {

    private val _getMovieDetails = MutableLiveData<MovieDetailsResponse>()
    val getMovieDetails = _getMovieDetails as LiveData<MovieDetailsResponse>

    private val _getMovieCredits = MutableLiveData<MovieCreditsResponse>()
    val getMovieCredits = _getMovieCredits as LiveData<MovieCreditsResponse>

    private val _getMovieRecommendations = MutableLiveData<MovieResponse>()
    val getMovieRecommendations = _getMovieRecommendations as LiveData<MovieResponse>

    private val _getMovieSimilar = MutableLiveData<MovieResponse>()
    val getMovieSimilar = _getMovieSimilar as LiveData<MovieResponse>

    private val _getMovieProviders = MutableLiveData<ProviderResponse>()
    val getMovieProviders = _getMovieProviders as LiveData<ProviderResponse>

    private val _getMovieReviews = MutableLiveData<ReviewResponse>()
    val getMovieReviews = _getMovieReviews as LiveData<ReviewResponse>

    private val _getMovieVideos = MutableLiveData<VideoResponse>()
    val getMovieVideos = _getMovieVideos as LiveData<VideoResponse>

    private val _getMovieImages = MutableLiveData<ImageResponse>()
    val getMovieImages = _getMovieImages as LiveData<ImageResponse>

    fun initViewModel(id: Int) {
        getMovieRecommendations(id = id)
        getMovieProviders(id = id)
        getMovieDetails(id = id)
        getMovieCredits(id = id)
        getMovieSimilar(id = id)
        getMovieReviews(id = id)
        getMovieVideos(id = id)
        getMovieImages(id = id)
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieDetails.invoke(id = id)) {
                is Resource.Success -> {
                    _getMovieDetails.postValue(response.data!!)
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

    fun getMovieCredits(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieCredits.invoke(id = id)) {
                is Resource.Success -> {
                    _getMovieCredits.postValue(response.data!!)
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

    fun getMovieRecommendations(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieRecommendations.invoke(id = id, page = 1)) {
                is Resource.Success -> {
                    _getMovieRecommendations.postValue(response.data!!)
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

    fun getMovieSimilar(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieSimilar.invoke(id = id, page = 1)) {
                is Resource.Success -> {
                    _getMovieSimilar.postValue(response.data!!)
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

    fun getMovieProviders(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieProviders.invoke(id = id)) {
                is Resource.Success -> {
                    _getMovieProviders.postValue(response.data!!)
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

    fun getMovieReviews(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieReviews.invoke(id = id, page = 1)) {
                is Resource.Success -> {
                    _getMovieReviews.postValue(response.data!!)
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

    fun getMovieVideos(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieVideos.invoke(id = id)) {
                is Resource.Success -> {
                    _getMovieVideos.postValue(response.data!!)
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

    fun getMovieImages(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getMovieImages.invoke(id = id)) {
                is Resource.Success -> {
                    _getMovieImages.postValue(response.data!!)
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
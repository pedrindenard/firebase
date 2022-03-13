package com.pdm.firebase.feature.presentation.fragment.details.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.movie.CreditsResponse
import com.pdm.firebase.feature.domain.model.details.Requests
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.model.tv.details.TvDetailsResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.usecase.TvDetailsUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class TvShowViewModel(private val useCase: TvDetailsUseCase) : BaseViewModel() {

    private val _getTvDetails = MutableLiveData<TvDetailsResponse>()
    val getTvDetails = _getTvDetails as LiveData<TvDetailsResponse>

    private val _getTvCredits = MutableLiveData<CreditsResponse>()
    val getTvCredits = _getTvCredits as LiveData<CreditsResponse>

    private val _getTvRecommendations = MutableLiveData<TvShowResponse>()
    val getTvRecommendations = _getTvRecommendations as LiveData<TvShowResponse>

    private val _getTvSimilar = MutableLiveData<TvShowResponse>()
    val getTvSimilar = _getTvSimilar as LiveData<TvShowResponse>

    private val _getTvProviders = MutableLiveData<ProviderResponse>()
    val getTvProviders = _getTvProviders as LiveData<ProviderResponse>

    private val _getTvReviews = MutableLiveData<ReviewResponse>()
    val getTvReviews = _getTvReviews as LiveData<ReviewResponse>

    private val _getTvVideos = MutableLiveData<VideoResponse>()
    val getTvVideos = _getTvVideos as LiveData<VideoResponse>

    private val _getTvImages = MutableLiveData<ImageResponse>()
    val getTvImages = _getTvImages as LiveData<ImageResponse>

    private val _onSuccess = MutableLiveData<Unit>()
    val onSuccess = _onSuccess as LiveData<Unit>

    private var listRequest = Requests.get(quantity = 8)

    fun initViewModel(id: Int) {
        getTvRecommendations(id = id)
        getTvProviders(id = id)
        getTvDetails(id = id)
        getTvCredits(id = id)
        getTvSimilar(id = id)
        getTvReviews(id = id)
        getTvVideos(id = id)
        getTvImages(id = id)
    }

    private fun getTvDetails(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvDetails.invoke(id = id)) {
                is Resource.Success -> { onSuccess(position = 0)
                    _getTvDetails.postValue(response.data!!)
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

    private fun getTvCredits(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvCredits.invoke(id = id)) {
                is Resource.Success -> { onSuccess(position = 1)
                    _getTvCredits.postValue(response.data!!)
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

    private fun getTvRecommendations(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvRecommendations.invoke(id = id, page = 1)) {
                is Resource.Success -> { onSuccess(position = 2)
                    _getTvRecommendations.postValue(response.data!!)
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

    private fun getTvSimilar(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvSimilar.invoke(id = id, page = 1)) {
                is Resource.Success -> { onSuccess(position = 3)
                    _getTvSimilar.postValue(response.data!!)
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

    private fun getTvProviders(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvProviders.invoke(id = id)) {
                is Resource.Success -> { onSuccess(position = 4)
                    _getTvProviders.postValue(response.data!!)
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

    private fun getTvReviews(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvReviews.invoke(id = id, page = 1)) {
                is Resource.Success -> { onSuccess(position = 5)
                    _getTvReviews.postValue(response.data!!)
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

    private fun getTvVideos(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvVideos.invoke(id = id)) {
                is Resource.Success -> { onSuccess(position = 6)
                    _getTvVideos.postValue(response.data!!)
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

    private fun getTvImages(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getTvImages.invoke(id = id)) {
                is Resource.Success -> { onSuccess(position = 7)
                    _getTvImages.postValue(response.data!!)
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

    private fun onSuccess(position: Int) {
        listRequest.run {
            this[position] = true
            forEach {
                if (!it) {
                    return
                }
            }
        }
        _onSuccess.postValue(Unit)
    }
}
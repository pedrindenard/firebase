package com.pdm.firebase.feature.presentation.fragment.details.people.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.people.PeopleResponse
import com.pdm.firebase.feature.domain.model.people.details.PeopleDetailsResponse
import com.pdm.firebase.feature.domain.model.tagged.TaggedResponse
import com.pdm.firebase.feature.domain.usecase.PeopleUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class PeopleViewModel(private val useCase: PeopleUseCase) : BaseViewModel() {

    private val _getPeopleDetails = MutableLiveData<PeopleDetailsResponse>()
    val getPeopleDetails = _getPeopleDetails as LiveData<PeopleDetailsResponse>

    private val _getPeopleCredits = MutableLiveData<PeopleCreditsResponse>()
    val getPeopleCredits = _getPeopleCredits as LiveData<PeopleCreditsResponse>

    private val _getPeopleTagged = MutableLiveData<TaggedResponse>()
    val getPeopleTagged = _getPeopleTagged as LiveData<TaggedResponse>

    private val _getPeopleImages = MutableLiveData<ImageResponse>()
    val getPeopleImages = _getPeopleImages as LiveData<ImageResponse>

    private val _getBestPeople: MutableLiveData<PeopleResponse> = MutableLiveData()
    val getBestActors = _getBestPeople as LiveData<PeopleResponse>

    fun initViewModel(id: Int) {
        getPeopleDetails(id = id)
        getPeopleCredits(id = id)
        getPeopleTagged(id = id)
        getPeopleImages(id = id)
    }

    private fun getPeopleDetails(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getPeopleDetails.invoke(id = id)) {
                is Resource.Success -> {
                    _getPeopleDetails.postValue(response.data!!)
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

    private fun getPeopleCredits(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getPeopleCredits.invoke(id = id)) {
                is Resource.Success -> {
                    _getPeopleCredits.postValue(response.data!!)
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

    private fun getPeopleTagged(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getPeopleTagged.invoke(id = id, page = 1)) {
                is Resource.Success -> {
                    _getPeopleTagged.postValue(response.data!!)
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

    private fun getPeopleImages(id: Int) {
        viewModelScope.launch {
            when (val response = useCase.getPeopleImages.invoke(id = id)) {
                is Resource.Success -> {
                    _getPeopleImages.postValue(response.data!!)
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
            when (val response = useCase.getBestActors.invoke(page = 1, ignoreCache)) {
                is Resource.Success -> {
                    _getBestPeople.postValue(response.data!!)
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
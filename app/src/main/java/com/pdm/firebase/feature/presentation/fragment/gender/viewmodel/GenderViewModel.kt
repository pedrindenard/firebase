package com.pdm.firebase.feature.presentation.fragment.gender.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.TvShowUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class GenderViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvShowUseCase: TvShowUseCase
) : BaseViewModel() {

    private val _getGendersMovie: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGendersMovie = _getGendersMovie as LiveData<GenderResponse>

    private val _getGendersTV: MutableLiveData<GenderResponse> = MutableLiveData()
    val getGendersTV = _getGendersTV as LiveData<GenderResponse>

    fun getGendersMovie() {
        viewModelScope.launch {
            when (val response = movieUseCase.getGendersMovie.invoke()) {
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

    fun getGendersTv() {
        viewModelScope.launch {
            when (val response = tvShowUseCase.getGendersTvShow.invoke()) {
                is Resource.Success -> {
                    _getGendersTV.postValue(response.data!!)
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
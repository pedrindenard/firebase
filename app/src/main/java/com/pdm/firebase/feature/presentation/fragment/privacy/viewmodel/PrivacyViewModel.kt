package com.pdm.firebase.feature.presentation.fragment.privacy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.feature.data.fromto.fromDataToPrivacy
import com.pdm.firebase.feature.domain.model.privacy.Privacy
import com.pdm.firebase.feature.domain.usecase.PrivacyUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import com.pdm.firebase.util.BAD_REQUEST
import kotlinx.coroutines.launch

class PrivacyViewModel(private val useCase: PrivacyUseCase) : BaseViewModel() {

    private val _successGetPrivacy = MutableLiveData<Privacy?>()
    val successGetPrivacy = _successGetPrivacy as LiveData<Privacy?>

    fun getPrivacy() {
        viewModelScope.launch {
            try {
                useCase()?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successGetPrivacy.postValue(it.fromDataToPrivacy())
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: Exception) {
                when (e.message) {
                    BAD_REQUEST -> {
                        errorResponse.postValue("")
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }
}
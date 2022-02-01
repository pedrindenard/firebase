package com.pdm.firebasestoragedatabase.feature.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val errorResponse: MutableLiveData<String> = MutableLiveData()

    val failureResponse: MutableLiveData<Throwable> = MutableLiveData()

}
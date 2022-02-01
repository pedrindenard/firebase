package com.pdm.firebasestoragedatabase.feature.presentation.fragment.recovery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.pdm.firebasestoragedatabase.feature.domain.enums.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.enums.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.usecase.AuthUseCase
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class RecoveryViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val emailError = MutableLiveData<Unit>()

    private val _successRecoveryPassword = MutableLiveData<Task<Void>?>()
    val successRecoveryPassword = _successRecoveryPassword as LiveData<Task<Void>?>

    fun recoveryPassword(email: String) {
        viewModelScope.launch {
            try {
                useCase.recoveryUserUseCase(email)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successRecoveryPassword.postValue(it)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                when (e.message) {
                    InvalidAuth.INVALID_EMAIL.value -> {
                        emailError.postValue(Unit)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }
}
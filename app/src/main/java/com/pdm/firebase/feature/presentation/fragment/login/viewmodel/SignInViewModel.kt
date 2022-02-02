package com.pdm.firebase.feature.presentation.fragment.login.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.data.fromto.fromDataToUserGoogle
import com.pdm.firebase.feature.domain.enums.AuthException
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.UserGoogle
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val emailError = MutableLiveData<Unit>()
    val passwordError = MutableLiveData<Unit>()

    private val _successLogin = MutableLiveData<Task<AuthResult?>>()
    val successLoginWithUser = _successLogin as LiveData<Task<AuthResult?>>

    private val _successCreatedUserWithSocial = MutableLiveData<UserGoogle>()
    val successCreatedUserWithSocial = _successCreatedUserWithSocial as LiveData<UserGoogle>

    fun loginWithUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                useCase.loginWithUserUseCase(email, password)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successLogin.postValue(it)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                e.message?.let {
                    if (it.contains(InvalidAuth.INVALID_EMAIL.value)) {
                        emailError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_PASSWORD.value)) {
                        passwordError.postValue(Unit)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }

    fun loginWithGoogle(data: Intent?) {
        viewModelScope.launch {
            try {
                useCase.loginWithGoogleUseCase(data)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            when (it.result.additionalUserInfo!!.isNewUser) {
                                true -> {
                                    _successCreatedUserWithSocial.postValue(it.fromDataToUserGoogle())
                                }
                                false -> {
                                    _successLogin.postValue(it)
                                }
                            }
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                when (e.message) {
                    InvalidAuth.SIGN_IN_FAILED.value -> {
                        errorResponse.postValue(e.message)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }
}
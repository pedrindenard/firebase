package com.pdm.firebase.feature.presentation.fragment.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.data.fromto.fromDataToUserFacebook
import com.pdm.firebase.feature.data.fromto.fromDataToUserGitHub
import com.pdm.firebase.feature.data.fromto.fromDataToUserGoogle
import com.pdm.firebase.feature.domain.enums.AuthException
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val emailError = MutableLiveData<Unit?>()
    val passwordError = MutableLiveData<Unit?>()
    val invalidNumberError = MutableLiveData<Unit?>()
    val dismissDialogFragment = MutableLiveData<Unit?>()

    private val _successLogin = MutableLiveData<Task<AuthResult?>>()
    val successLoginWithUser = _successLogin as LiveData<Task<AuthResult?>>

    private val _successCreatedUserWithSocial = MutableLiveData<User>()
    val successCreatedUserWithSocialLogin = _successCreatedUserWithSocial as LiveData<User>

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

    fun loginWithGoogle(accessToken: String?) {
        viewModelScope.launch {
            try {
                useCase.loginWithGoogleUseCase(accessToken)?.addOnCompleteListener {
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

    fun loginWithFacebook(accessToken: AccessToken?) {
        viewModelScope.launch {
            try {
                useCase.loginWithFacebookUseCase(accessToken)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            when (it.result.additionalUserInfo!!.isNewUser) {
                                true -> {
                                    _successCreatedUserWithSocial.postValue(it.fromDataToUserFacebook())
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

    fun loginWithGitHub(task: Task<AuthResult>, email: String) {
        viewModelScope.launch {
            useCase.loginWithGitHubUseCase(task)?.addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        when (it.result.additionalUserInfo!!.isNewUser) {
                            true -> {
                                _successCreatedUserWithSocial.postValue(it.fromDataToUserGitHub(email))
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
                dismissDialogFragment.postValue(Unit)
            }
        }
    }

    fun loginWithNumberPhone(numberPhone: String) {
        viewModelScope.launch {
            try {
                useCase.loginWithNumberProneUseCase(numberPhone)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            when (it.result.additionalUserInfo!!.isNewUser) {
                                true -> {

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
                    InvalidAuth.INVALID_NUMBER_PHONE.value -> {
                        invalidNumberError.postValue(Unit)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }

    fun invalidateErrors() {
        dismissDialogFragment.value = null
        invalidNumberError.value = null
        passwordError.value = null
        emailError.value = null
    }
}
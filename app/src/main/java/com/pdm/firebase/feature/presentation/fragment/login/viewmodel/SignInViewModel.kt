package com.pdm.firebase.feature.presentation.fragment.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.pdm.firebase.feature.data.fromto.fromDataToUserFacebook
import com.pdm.firebase.feature.data.fromto.fromDataToUserGitHub
import com.pdm.firebase.feature.data.fromto.fromDataToUserGoogle
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val emailError = MutableLiveData<Unit?>()
    val passwordError = MutableLiveData<Unit?>()
    val invalidNumberError = MutableLiveData<Unit?>()

    private val _successLogin = MutableLiveData<Task<AuthResult?>?>()
    val successLoginWithUser = _successLogin as LiveData<Task<AuthResult?>?>

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
            } catch (e: Exception) {
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
                                    addInfoToSocialUser(
                                        user = it.fromDataToUserGoogle()!!,
                                        uid = it.result.user?.uid
                                    )
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
            } catch (e: java.lang.Exception) {
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
                                    addInfoToSocialUser(
                                        user = it.fromDataToUserFacebook()!!,
                                        uid = it.result.user?.uid
                                    )
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
            } catch (e: java.lang.Exception) {
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
                                addInfoToSocialUser(
                                    user = it.fromDataToUserGitHub(email)!!,
                                    uid = it.result.user?.uid
                                )
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
        }
    }

    fun loginWithNumberPhone(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
                useCase.loginWithNumberProneUseCase(credential)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            when (it.result.additionalUserInfo!!.isNewUser) {
                                true -> {
                                    addInfoToSocialUser(
                                        uid = it.result.user?.uid,
                                        user = User()
                                    )
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
            } catch (e: Exception) {
                when (e.message) {
                    InvalidAuth.INVALID_NUMBER_PHONE.value -> {
                        invalidNumberError.postValue(Unit)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }

    private fun addInfoToSocialUser(user: User, uid: String?) {
        viewModelScope.launch {
            try {
                useCase.addInfoSocialUserUseCase(uid, user)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successLogin.postValue(null)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: Exception) {
                when (e.message) {
                    InvalidAuth.CURRENT_USER_IS_NULL.value -> {
                        errorResponse.postValue("")
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }
}
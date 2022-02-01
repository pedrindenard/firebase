package com.pdm.firebasestoragedatabase.feature.presentation.fragments.login.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidUser
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.usecase.AuthUseCase
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val emailError = MutableLiveData<Unit>()
    val passwordError = MutableLiveData<Unit>()
    val invalidFields = MutableLiveData<Unit>()

    private val _successLogin = MutableLiveData<Task<AuthResult?>>()
    val successLoginWithUser = _successLogin as LiveData<Task<AuthResult?>>

    private val _successLoginWithSocial = MutableLiveData<Pair<User?, Boolean>>()
    val successLoginWithSocial = _successLoginWithSocial as LiveData<Pair<User?, Boolean>>

    fun loginWithUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                useCase.loginWithUserUseCase(email, password)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successLogin.postValue(it)
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
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
                invalidFields.postValue(Unit)
            }
        }
    }

    fun loginWithGoogle(firebaseAuth: FirebaseAuth, data: Intent?) {
        viewModelScope.launch {
            try {
                useCase.loginWithGoogleUseCase(data)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            getUserInfo(
                                uid = firebaseAuth.currentUser?.uid
                            )
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                when (e.message) {
                    InvalidAuth.SIGN_IN_FAILED.value -> {
                        errorResponse.postValue("")
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }

    private fun getUserInfo(uid: String?) {
        viewModelScope.launch {
            try {
                useCase.getUserInfoUseCase(uid)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            val user = it.result.getValue(User::class.java)

                            _successLoginWithSocial.postValue(
                                Pair(user, user?.lastName != null &&
                                     user.birthdate?.isNotEmpty() == true &&
                                     user.legalDocument?.isNotEmpty() == true
                                )
                            )
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                when (e.message) {
                    InvalidUser.INVALID_UID.value -> {
                        errorResponse.postValue("")
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }
}
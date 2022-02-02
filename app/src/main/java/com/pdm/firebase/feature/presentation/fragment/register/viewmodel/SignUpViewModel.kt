package com.pdm.firebase.feature.presentation.fragment.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.domain.enums.AuthException
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.User
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val nameEmpty = MutableLiveData<Unit>()
    val lastNameEmpty = MutableLiveData<Unit>()
    val birthDateError = MutableLiveData<Unit>()
    val emailError = MutableLiveData<Unit>()
    val legalDocumentError = MutableLiveData<Unit>()
    val passwordError = MutableLiveData<Unit>()
    val confirmPasswordError = MutableLiveData<Unit>()

    private val _successCreateUser = MutableLiveData<Boolean?>()
    val successCreateUser = _successCreateUser as LiveData<Boolean?>

    fun registerWithUser(user: User, password: String, confirmPassword: String, firebaseAuth: FirebaseAuth) {
        viewModelScope.launch {
            try {
                useCase.registerUserUseCase(user, password, confirmPassword)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            addInfoToUser(
                                firebaseAuth = firebaseAuth,
                                user = user
                            )
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                e.message?.let {
                    if (it.contains(InvalidAuth.EMPTY_NAME.value)) {
                        nameEmpty.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.EMPTY_LAST_NAME.value)) {
                        lastNameEmpty.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_EMAIL.value)) {
                        emailError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_LEGAL_DOCUMENT.value)) {
                        legalDocumentError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_BIRTHDATE.value)) {
                        birthDateError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_PASSWORD.value)) {
                        passwordError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_CONFIRM_PASSWORD.value)) {
                        confirmPasswordError.postValue(Unit)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }

    fun addInfoToUser(user: User, firebaseAuth: FirebaseAuth) {
        viewModelScope.launch {
            try {
                useCase.addInfoUserUseCase(firebaseAuth, user)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            when (firebaseAuth.currentUser!!.isEmailVerified) {
                                true -> {
                                    _successCreateUser.postValue(false)
                                }
                                false -> {
                                    submitEmailVerification(
                                        firebaseAuth = firebaseAuth
                                    )
                                }
                            }
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                e.message?.let {
                    if (it.contains(InvalidAuth.CURRENT_USER_IS_NULL.value)) {
                        errorResponse.postValue("")
                    }
                    if (it.contains(InvalidAuth.EMPTY_NAME.value)) {
                        nameEmpty.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.EMPTY_LAST_NAME.value)) {
                        lastNameEmpty.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_EMAIL.value)) {
                        emailError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_LEGAL_DOCUMENT.value)) {
                        legalDocumentError.postValue(Unit)
                    }
                    if (it.contains(InvalidAuth.INVALID_BIRTHDATE.value)) {
                        birthDateError.postValue(Unit)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }

    private fun submitEmailVerification(firebaseAuth: FirebaseAuth) {
        viewModelScope.launch {
            try {
                useCase.emailVerificationUseCase(firebaseAuth)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successCreateUser.postValue(true)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
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
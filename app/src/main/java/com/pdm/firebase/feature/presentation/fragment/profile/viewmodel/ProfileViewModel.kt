package com.pdm.firebase.feature.presentation.fragment.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.data.fromto.fromDataToUser
import com.pdm.firebase.feature.domain.enums.AuthException
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.enums.InvalidUser
import com.pdm.firebase.feature.domain.model.User
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val nameEmpty = MutableLiveData<Unit>()
    val lastNameEmpty = MutableLiveData<Unit>()
    val birthDateError = MutableLiveData<Unit>()
    val emailError = MutableLiveData<Unit>()
    val legalDocumentError = MutableLiveData<Unit>()
    val passwordError = MutableLiveData<Unit>()
    val confirmPasswordError = MutableLiveData<Unit>()
    val errorLogout = MutableLiveData<Unit>()

    private val _successGetUserInfo = MutableLiveData<User?>()
    val successGetUserInfo = _successGetUserInfo as LiveData<User?>

    private val _successEditUserInfo = MutableLiveData<Task<Void>?>()
    val successEditUserInfo = _successEditUserInfo as LiveData<Task<Void>?>

    private val _successLogOut = MutableLiveData<Unit?>()
    val successLogOut = _successLogOut as LiveData<Unit?>

    fun editProfile(firebaseAuth: FirebaseAuth, user: User) {
        viewModelScope.launch {
            try {
                useCase.editUserUseCase(firebaseAuth, user)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successEditUserInfo.postValue(it)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                e.message?.let {
                    if (it.contains(InvalidAuth.CURRENT_USER_IS_NULL.value)) {
                        errorResponse.postValue(e.message)
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

    private fun getUserInfo(uid: String?) {
        viewModelScope.launch {
            try {
                useCase.getUserInfoUseCase(uid)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successGetUserInfo.postValue(it.fromDataToUser())
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: AuthException) {
                when (e.message) {
                    InvalidUser.INVALID_UID.value -> {
                        errorResponse.postValue(e.message)
                    }
                }
                failureResponse.postValue(e)
            }
        }
    }
}
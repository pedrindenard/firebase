package com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthFields
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.use_case.AuthUseCase
import com.pdm.firebasestoragedatabase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(private val useCase: AuthUseCase) : BaseViewModel() {

    val fullNameEmpty = MutableLiveData<String>()
    val birthDateError = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val invalidFields = MutableLiveData<Unit>()
    val errorLogout = MutableLiveData<Unit>()

    private val _successCreateUser = MutableLiveData<Pair<User, Boolean?>>()
    val successCreateUser = _successCreateUser as LiveData<Pair<User, Boolean?>>

    private val _successEditUserInfo = MutableLiveData<Task<Void>?>()
    val successEditUserInfo = _successEditUserInfo as LiveData<Task<Void>?>

    private val _successLogin = MutableLiveData<Task<AuthResult?>>()
    val successLogin = _successLogin as LiveData<Task<AuthResult?>>

    private val _successRecoveryPassword = MutableLiveData<Task<Void>?>()
    val successRecoveryPassword = _successRecoveryPassword as LiveData<Task<Void>?>

    private val _successSubmitEmailVerification = MutableLiveData<Task<Void>?>()
    val successSubmitEmailVerification = _successSubmitEmailVerification as LiveData<Task<Void>?>

    private val _successLogOut = MutableLiveData<Unit?>()
    val successLogOut = _successLogOut as LiveData<Unit?>

    fun Context.registerFirebase(name: String, date: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = User(name = name, email = email, age = date)
                useCase.registerUser(user, password)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successCreateUser.postValue(Pair(user, true))
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: InvalidAuthException) {
                when (e.message) {
                    InvalidAuthFields.EMPTY_NAME.exception -> {
                        fullNameEmpty.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_PASSWORD.exception -> {
                        passwordError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_BIRTHDATE.exception -> {
                        birthDateError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.INVALID_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_email)
                        )
                    }
                    InvalidAuthFields.INVALID_PASSWORD.exception -> {
                        passwordError.postValue(
                            getString(R.string.error_password)
                        )
                    }
                    InvalidAuthFields.INVALID_BIRTHDATE.exception -> {
                        birthDateError.postValue(
                            getString(R.string.error_date)
                        )
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }

    fun Context.loginFirebase(email: String, password: String) {
        viewModelScope.launch {
            try {
                useCase.loginUser(email, password)?.addOnCompleteListener {
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
            } catch (e: InvalidAuthException) {
                when (e.message) {
                    InvalidAuthFields.EMPTY_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_PASSWORD.exception -> {
                        passwordError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.INVALID_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_email)
                        )
                    }
                    InvalidAuthFields.INVALID_PASSWORD.exception -> {
                        passwordError.postValue(
                            getString(R.string.error_password)
                        )
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }

    fun Context.editAuthenticationFirebase(firebaseAuth: FirebaseAuth, user: User) {
        viewModelScope.launch {
            try {
                useCase.editUser(firebaseAuth, user)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successEditUserInfo.postValue(it)
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: InvalidAuthException) {
                when (e.message) {
                    InvalidAuthFields.CURRENT_USER_IS_NULL.exception -> {
                        errorResponse.postValue(
                            getString(R.string.error_register_user)
                        )
                    }
                    InvalidAuthFields.EMPTY_NAME.exception -> {
                        fullNameEmpty.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_PASSWORD.exception -> {
                        passwordError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.EMPTY_BIRTHDATE.exception -> {
                        birthDateError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.INVALID_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_email)
                        )
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }

    fun Context.recoveryPassword(email: String) {
        viewModelScope.launch {
            try {
                useCase.recoveryUser(email)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successRecoveryPassword.postValue(it)
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: InvalidAuthException) {
                when (e.message) {
                    InvalidAuthFields.EMPTY_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_empty_field)
                        )
                    }
                    InvalidAuthFields.INVALID_EMAIL.exception -> {
                        emailError.postValue(
                            getString(R.string.error_email)
                        )
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }

    fun Context.submitEmailVerification(firebaseAuth: FirebaseAuth) {
        viewModelScope.launch {
            try {
                useCase.submitEmailUserAuth(firebaseAuth)?.addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            _successSubmitEmailVerification.postValue(it)
                        }
                        it.isCanceled -> {
                            failureResponse.postValue(it.exception)
                        }
                        else -> {
                            errorResponse.postValue(it.exception?.message)
                        }
                    }
                }
            } catch (e: InvalidAuthException) {
                when (e.message) {
                    InvalidAuthFields.CURRENT_USER_IS_NULL.exception -> {
                        errorResponse.postValue(
                            getString(R.string.error_register_user)
                        )
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }

    fun logOut(firebaseAuth: FirebaseAuth) {
        viewModelScope.launch {
            try {
                useCase.logOut(firebaseAuth).let {
                    _successLogOut.postValue(Unit)
                }
            } catch (e: InvalidAuthException) {
                when (e.message) {
                    InvalidAuthFields.CURRENT_USER_IS_NULL.exception -> {
                        errorLogout.postValue(Unit)
                    }
                }
                invalidFields.postValue(Unit)
            }
        }
    }
}
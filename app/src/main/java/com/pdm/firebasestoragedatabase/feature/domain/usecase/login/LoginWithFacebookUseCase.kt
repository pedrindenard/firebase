package com.pdm.firebasestoragedatabase.feature.domain.usecase.login

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.repository.LoginRepository

class LoginWithFacebookUseCase(private val repository: LoginRepository) {

    @Throws(AuthException::class)
    suspend operator fun invoke(data: Intent?): Task<AuthResult>? {
        if (data == null) {
            throw AuthException(InvalidAuth.SIGN_IN_FAILED.value)
        }

        return repository.loginWithFacebook(
            data = data
        )
    }
}
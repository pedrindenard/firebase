package com.pdm.firebasestoragedatabase.feature.domain.usecase.login

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebasestoragedatabase.feature.domain.enums.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.enums.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.repository.LoginRepository

class LoginWithGitHubUseCase(private val repository: LoginRepository) {

    @Throws(AuthException::class)
    suspend operator fun invoke(data: Intent?): Task<AuthResult>? {
        if (data == null) {
            throw AuthException(InvalidAuth.SIGN_IN_FAILED.value)
        }

        return repository.loginWithGitHub(
            data = data
        )
    }
}
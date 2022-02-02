package com.pdm.firebase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.enums.AuthException
import com.pdm.firebase.feature.domain.repository.LoginRepository
import com.pdm.firebase.util.isValidEmail
import com.pdm.firebase.util.isValidPassword

class LoginWithUserUseCase(private val repository: LoginRepository) {

    @Throws(AuthException::class)
    suspend operator fun invoke(email: String, password: String): Task<AuthResult>? {
        var throws = String()

        if (!isValidEmail(email)) {
            throws += InvalidAuth.INVALID_EMAIL.value
        }
        if (!isValidPassword(password)) {
            throws += InvalidAuth.INVALID_PASSWORD.value
        }
        if (throws.isNotEmpty()) {
            throw AuthException(throws)
        }

        return repository.loginWithUser(
            email = email,
            password = password
        )
    }
}
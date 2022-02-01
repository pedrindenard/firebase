package com.pdm.firebasestoragedatabase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.repository.LoginRepository
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.isValidPassword

class LoginWithUserUseCase(private val repository: LoginRepository) {

    private var throws: String = ""

    @Throws(AuthException::class)
    suspend operator fun invoke(email: String, password: String): Task<AuthResult>? {
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
package com.pdm.firebasestoragedatabase.feature.domain.use_case.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.*
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.isValidPassword

class LoginUserAuth(private val repository: AuthRepository) {

    @Throws(InvalidAuthException::class)
    suspend operator fun invoke(email: String, password: String) : Task<AuthResult>? {
        when {
            email.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_EMAIL.exception)
            }
            !isValidEmail(email) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_EMAIL.exception)
            }
            password.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_PASSWORD.exception)
            }
            !isValidPassword(password) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_PASSWORD.exception)
            }
        }

        return repository.loginUserAuth(
            email = email,
            password = password
        )
    }
}
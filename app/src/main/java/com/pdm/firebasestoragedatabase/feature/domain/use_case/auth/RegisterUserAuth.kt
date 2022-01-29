package com.pdm.firebasestoragedatabase.feature.domain.use_case.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthFields
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository
import com.pdm.firebasestoragedatabase.util.isValidBirthDate
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.isValidPassword

class RegisterUserAuth(private val repository: AuthRepository) {

    @Throws(InvalidAuthException::class)
    suspend operator fun invoke(user: User, password: String) : Task<AuthResult>? {
        when {
            user.name.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_NAME.exception)
            }
            user.age.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_BIRTHDATE.exception)
            }
            !isValidBirthDate(user.age) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_BIRTHDATE.exception)
            }
            user.email.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_EMAIL.exception)
            }
            !isValidEmail(user.email) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_EMAIL.exception)
            }
            password.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_PASSWORD.exception)
            }
            !isValidPassword(password) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_PASSWORD.exception)
            }
        }

        return repository.registerUserAuth(
            password = password,
            email = user.email
        )
    }
}
package com.pdm.firebasestoragedatabase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebasestoragedatabase.feature.domain.enums.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.enums.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.repository.RegisterRepository
import com.pdm.firebasestoragedatabase.util.isValidBirthDate
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.isValidLegalDocument
import com.pdm.firebasestoragedatabase.util.isValidPassword

class RegisterUserUseCase(private val repository: RegisterRepository) {

    @Throws(AuthException::class)
    suspend operator fun invoke(user: User, password: String, confirmPassword: String): Task<AuthResult>? {
        var throws = String()

        if (user.name.isBlank()) {
            throws += InvalidAuth.EMPTY_NAME.value
        }
        if (user.lastName.isBlank()) {
            throws += InvalidAuth.EMPTY_LAST_NAME.value
        }
        if (!isValidEmail(user.email)) {
            throws += InvalidAuth.INVALID_EMAIL.value
        }
        if (!isValidLegalDocument(user.legalDocument)) {
            throws += InvalidAuth.INVALID_LEGAL_DOCUMENT.value
        }
        if (!isValidBirthDate(user.birthdate)) {
            throws += InvalidAuth.INVALID_BIRTHDATE.value
        }
        if (!isValidPassword(password)) {
            throws += InvalidAuth.INVALID_PASSWORD.value
        }
        if (password != confirmPassword) {
            throws += InvalidAuth.INVALID_CONFIRM_PASSWORD.value
        }
        if (throws.isNotEmpty()) {
            throw AuthException(throws)
        }

        return repository.registerWithUser(
            password = password,
            email = user.email
        )
    }
}
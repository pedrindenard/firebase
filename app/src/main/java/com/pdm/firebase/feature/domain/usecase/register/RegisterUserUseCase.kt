package com.pdm.firebase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.repository.RegisterRepository
import com.pdm.firebase.util.isValidBirthDate
import com.pdm.firebase.util.isValidEmail
import com.pdm.firebase.util.isValidPassword

class RegisterUserUseCase(private val repository: RegisterRepository) {

    @Throws(Exception::class)
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
            throw Exception(throws)
        }

        return repository.registerWithUser(
            password = password,
            email = user.email
        )
    }
}
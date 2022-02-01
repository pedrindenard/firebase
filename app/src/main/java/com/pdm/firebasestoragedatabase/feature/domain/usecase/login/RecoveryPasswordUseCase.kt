package com.pdm.firebasestoragedatabase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.repository.LoginRepository
import com.pdm.firebasestoragedatabase.util.isValidEmail

class RecoveryPasswordUseCase(private val repository: LoginRepository) {

    @Throws(AuthException::class)
    suspend operator fun invoke(email: String) : Task<Void>? {
        if (!isValidEmail(email)) {
            throw AuthException(InvalidAuth.INVALID_EMAIL.value)
        }

        return repository.recoveryPassword(
            email = email
        )
    }
}
package com.pdm.firebase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.repository.LoginRepository
import com.pdm.firebase.util.isValidEmail

class RecoveryPasswordUseCase(private val repository: LoginRepository) {

    @Throws(Exception::class)
    suspend operator fun invoke(email: String) : Task<Void>? {
        if (!isValidEmail(email)) {
            throw Exception(InvalidAuth.INVALID_EMAIL.value)
        }

        return repository.recoveryPassword(
            email = email
        )
    }
}
package com.pdm.firebase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.repository.LoginRepository
import com.pdm.firebase.util.isValidPhone

class LoginWithNumberProneUseCase(private val repository: LoginRepository) {

    @Throws(Exception::class)
    suspend operator fun invoke(numberPhone: String): Task<AuthResult>? {
        if (isValidPhone(numberPhone)) {
            throw Exception(InvalidAuth.INVALID_NUMBER_PHONE.value)
        }

        return repository.loginWithNumberPhone(

        )
    }
}
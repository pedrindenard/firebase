package com.pdm.firebase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.pdm.firebase.feature.domain.repository.LoginRepository

class LoginWithNumberProneUseCase(private val repository: LoginRepository) {

    suspend operator fun invoke(credential: PhoneAuthCredential): Task<AuthResult>? {
        return repository.loginWithNumberPhone(
            credential = credential
        )
    }
}
package com.pdm.firebase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.repository.RegisterRepository

class EmailVerificationUseCase(private val repository: RegisterRepository) {

    @Throws(Exception::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth) : Task<Void>? {
        if (firebaseAuth.currentUser == null) {
            throw Exception(InvalidAuth.CURRENT_USER_IS_NULL.value)
        }

        return repository.submitEmailVerification()
    }
}
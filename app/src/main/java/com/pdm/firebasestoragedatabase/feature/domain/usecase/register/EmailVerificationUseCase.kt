package com.pdm.firebasestoragedatabase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.repository.RegisterRepository

class EmailVerificationUseCase(private val repository: RegisterRepository) {

    @Throws(AuthException::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth) : Task<Void>? {
        if (firebaseAuth.currentUser == null) {
            throw AuthException(InvalidAuth.CURRENT_USER_IS_NULL.value)
        }

        return repository.submitEmailVerification()
    }
}
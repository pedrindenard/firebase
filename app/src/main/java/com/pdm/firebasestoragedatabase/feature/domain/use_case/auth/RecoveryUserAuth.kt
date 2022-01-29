package com.pdm.firebasestoragedatabase.feature.domain.use_case.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthFields
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository
import com.pdm.firebasestoragedatabase.util.isValidEmail

class RecoveryUserAuth(private val repository: AuthRepository) {

    @Throws(InvalidAuthException::class)
    suspend operator fun invoke(email: String) : Task<Void>? {
        when {
            email.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_EMAIL.exception)
            }
            !isValidEmail(email) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_EMAIL.exception)
            }
        }

        return repository.recoveryUserAuth(email)
    }
}
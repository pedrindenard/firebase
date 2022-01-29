package com.pdm.firebasestoragedatabase.feature.domain.use_case.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthFields
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository

class DeleteUserAuth(private val repository: AuthRepository) {

    @Throws(InvalidAuthException::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth) : Task<Void>? {
        if (firebaseAuth.currentUser == null) {
            throw InvalidAuthException(InvalidAuthFields.CURRENT_USER_IS_NULL.exception)
        }

        return repository.deleteUserAuth(firebaseAuth.currentUser!!)
    }
}
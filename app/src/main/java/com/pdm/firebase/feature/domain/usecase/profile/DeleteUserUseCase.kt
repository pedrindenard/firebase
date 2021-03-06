package com.pdm.firebase.feature.domain.usecase.profile

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.domain.enums.InvalidUser
import com.pdm.firebase.feature.domain.repository.ProfileRepository

class DeleteUserUseCase(private val repository: ProfileRepository) {

    @Throws(Exception::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth) : Task<Void>? {
        if (firebaseAuth.currentUser == null) {
            throw Exception(InvalidUser.CURRENT_USER_IS_NULL.value)
        }

        return repository.deleteUser(
            currentUser = firebaseAuth.currentUser!!
        )
    }
}
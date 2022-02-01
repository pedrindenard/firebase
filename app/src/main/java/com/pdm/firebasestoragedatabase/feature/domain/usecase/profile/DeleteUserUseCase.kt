package com.pdm.firebasestoragedatabase.feature.domain.usecase.profile

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.enums.InvalidUser
import com.pdm.firebasestoragedatabase.feature.domain.enums.ProfileException
import com.pdm.firebasestoragedatabase.feature.domain.repository.ProfileRepository

class DeleteUserUseCase(private val repository: ProfileRepository) {

    @Throws(ProfileException::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth) : Task<Void>? {
        if (firebaseAuth.currentUser == null) {
            throw ProfileException(InvalidUser.CURRENT_USER_IS_NULL.value)
        }

        return repository.deleteUser(
            currentUser = firebaseAuth.currentUser!!
        )
    }
}
package com.pdm.firebase.feature.domain.usecase.profile

import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.domain.enums.AuthException
import com.pdm.firebase.feature.domain.enums.InvalidUser
import com.pdm.firebase.feature.domain.enums.ProfileException
import com.pdm.firebase.feature.domain.repository.ProfileRepository

class LogOutUseCase(private val repository: ProfileRepository) {

    @Throws(ProfileException::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth) : Unit? {
        if (firebaseAuth.currentUser == null) {
            throw AuthException(InvalidUser.CURRENT_USER_IS_NULL.value)
        }

        return repository.logOutFirebase()
    }
}
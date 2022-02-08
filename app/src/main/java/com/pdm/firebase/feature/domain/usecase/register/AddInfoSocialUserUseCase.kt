package com.pdm.firebase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.repository.ProfileRepository

class AddInfoSocialUserUseCase(private val repository: ProfileRepository) {
    @Throws(Exception::class)
    suspend operator fun invoke(uid: String?, user: User): Task<Void>? {
        if (uid.isNullOrEmpty()) {
            throw Exception(InvalidAuth.CURRENT_USER_IS_NULL.value)
        }

        return repository.addUserInfo(
            user = user,
            uid = uid
        )
    }
}
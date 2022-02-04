package com.pdm.firebase.feature.domain.usecase.profile

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.pdm.firebase.feature.domain.enums.InvalidUser
import com.pdm.firebase.feature.domain.repository.ProfileRepository

class GetUserInfoUseCase(private val repository: ProfileRepository) {

    @Throws(Exception::class)
    suspend operator fun invoke(uid: String?): Task<DataSnapshot>? {
        if (uid == null || uid.isEmpty()) {
            throw Exception(InvalidUser.INVALID_UID.value)
        }

        return repository.getUserInfo(
            uid = uid
        )
    }
}
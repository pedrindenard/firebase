package com.pdm.firebasestoragedatabase.feature.domain.usecase.profile

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.core.SnapshotHolder
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidUser
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.ProfileException
import com.pdm.firebasestoragedatabase.feature.domain.repository.ProfileRepository

class GetUserInfoUseCase(private val repository: ProfileRepository) {

    @Throws(ProfileException::class)
    suspend operator fun invoke(uid: String?): Task<DataSnapshot>? {
        if (uid == null || uid.isEmpty()) {
            throw AuthException(InvalidUser.INVALID_UID.value)
        }

        return repository.getUserInfo(
            uid = uid
        )
    }
}
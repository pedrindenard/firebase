package com.pdm.firebase.feature.domain.usecase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.pdm.firebase.feature.data.fromto.fromDataToPrivacy
import com.pdm.firebase.feature.domain.repository.PrivacyRepository
import com.pdm.firebase.util.BAD_REQUEST

class PrivacyUseCase(private val repository: PrivacyRepository) {

    suspend operator fun invoke(): Task<DataSnapshot?>? {
        return repository.getPrivacy()
    }
}
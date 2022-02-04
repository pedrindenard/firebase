package com.pdm.firebase.feature.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.pdm.firebase.feature.domain.datasource.PrivacyDataSource
import com.pdm.firebase.feature.domain.repository.PrivacyRepository

class PrivacyRepositoryImpl(private val dataSource: PrivacyDataSource) : PrivacyRepository {

    override suspend fun getPrivacy(): Task<DataSnapshot?>? {
        return dataSource.getPrivacy()
    }
}
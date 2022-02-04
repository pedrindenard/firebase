package com.pdm.firebase.feature.domain.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface PrivacyDataSource {

    suspend fun getPrivacy(): Task<DataSnapshot?>?

}
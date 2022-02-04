package com.pdm.firebase.feature.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface PrivacyRepository {

    suspend fun getPrivacy(): Task<DataSnapshot?>?

}
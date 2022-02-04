package com.pdm.firebase.feature.data.datasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.pdm.firebase.feature.domain.datasource.PrivacyDataSource
import com.pdm.firebase.util.ERROR_SERVICE
import com.pdm.firebase.util.PRIVACY_DATABASE

class PrivacyDataSourceImpl : PrivacyDataSource {

    override suspend fun getPrivacy(): Task<DataSnapshot?>? {
        return try {
            FirebaseDatabase.getInstance().getReference(PRIVACY_DATABASE).get()
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }
}
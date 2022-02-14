package com.pdm.firebase.feature.data.datasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.pdm.firebase.feature.domain.datasource.ProfileDataSource
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.util.ERROR_SERVICE
import com.pdm.firebase.util.USERS_DATABASE

class ProfileDataSourceImpl : ProfileDataSource {

    override suspend fun getUserInfo(uid: String): Task<DataSnapshot>? {
        return try {
            FirebaseDatabase.getInstance().getReference(USERS_DATABASE)
                .child(uid)
                .get()
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun editProfile(currentUser: FirebaseUser, user: User): Task<Void>? {
        return try {
            FirebaseDatabase.getInstance().getReference(USERS_DATABASE)
                .child(currentUser.uid)
                .setValue(user)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun addUserInfo(uid: String, user: User): Task<Void>? {
        return try {
            FirebaseDatabase.getInstance().getReference(USERS_DATABASE)
                .child(uid)
                .setValue(user)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun deleteUser(currentUser: FirebaseUser): Task<Void>? {
        return try {
            currentUser.delete()
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }
}
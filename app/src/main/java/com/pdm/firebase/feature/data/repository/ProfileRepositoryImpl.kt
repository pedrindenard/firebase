package com.pdm.firebase.feature.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.pdm.firebase.feature.domain.datasource.ProfileDataSource
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource) : ProfileRepository {
    override suspend fun getUserInfo(uid: String): Task<DataSnapshot>? {
        return dataSource.getUserInfo(uid)
    }

    override suspend fun editProfile(currentUser: FirebaseUser, user: User): Task<Void>? {
        return dataSource.editProfile(currentUser, user)
    }

    override suspend fun addUserInfo(uid: String, user: User): Task<Void>? {
        return dataSource.addUserInfo(uid, user)
    }

    override suspend fun deleteUser(currentUser: FirebaseUser): Task<Void>? {
        return dataSource.deleteUser(currentUser)
    }
}
package com.pdm.firebasestoragedatabase.feature.domain.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.pdm.firebasestoragedatabase.feature.domain.model.User

interface ProfileDataSource {

    suspend fun getUserInfo(uid: String): Task<DataSnapshot>?

    suspend fun editProfile(currentUser: FirebaseUser, user: User): Task<Void>?

    suspend fun deleteUser(currentUser: FirebaseUser): Task<Void>?

    suspend fun logOutFirebase() : Unit?
}
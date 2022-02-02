package com.pdm.firebase.feature.domain.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.pdm.firebase.feature.domain.model.User

interface RegisterDataSource {

    suspend fun registerWithUser(email: String, password: String): Task<AuthResult>?

    suspend fun addInfoUser(currentUser: FirebaseUser, user: User): Task<Void>?

    suspend fun submitEmailVerification(): Task<Void>?

}
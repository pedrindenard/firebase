package com.pdm.firebasestoragedatabase.feature.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.pdm.firebasestoragedatabase.feature.domain.model.User

interface AuthRepository {

    suspend fun loginUserAuth(email: String, password: String): Task<AuthResult>?

    suspend fun registerUserAuth(email: String, password: String): Task<AuthResult>?

    suspend fun recoveryUserAuth(email: String): Task<Void>?

    suspend fun editUserAuth(currentUser: FirebaseUser, user: User): Task<Void>?

    suspend fun deleteUserAuth(currentUser: FirebaseUser): Task<Void>?

    suspend fun submitEmailVerification(): Task<Void>?
}
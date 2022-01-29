package com.pdm.firebasestoragedatabase.feature.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.pdm.firebasestoragedatabase.feature.domain.data_source.AuthDataSource
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {

    override suspend fun loginUserAuth(email: String, password: String): Task<AuthResult>? {
        return dataSource.loginUserAuth(email, password)
    }

    override suspend fun registerUserAuth(email: String, password: String): Task<AuthResult>? {
        return dataSource.registerUserAuth(email, password)
    }

    override suspend fun recoveryUserAuth(email: String): Task<Void>? {
        return dataSource.recoveryUserAuth(email)
    }

    override suspend fun editUserAuth(currentUser: FirebaseUser, user: User): Task<Void>? {
        return dataSource.editUserAuth(currentUser, user)
    }

    override suspend fun deleteUserAuth(currentUser: FirebaseUser): Task<Void>? {
        return dataSource.deleteUserAuth(currentUser)
    }

    override suspend fun submitEmailVerification(): Task<Void>? {
        return dataSource.submitEmailVerification()
    }

    override suspend fun logOut(): Unit? {
        return dataSource.logOut()
    }
}
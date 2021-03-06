package com.pdm.firebase.feature.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.pdm.firebase.feature.domain.datasource.RegisterDataSource
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.repository.RegisterRepository

class RegisterRepositoryImpl(private val dataSource: RegisterDataSource) : RegisterRepository {

    override suspend fun registerWithUser(email: String, password: String): Task<AuthResult>? {
        return dataSource.registerWithUser(email, password)
    }

    override suspend fun addInfoUser(currentUser: FirebaseUser, user: User): Task<Void>? {
        return dataSource.addInfoUser(currentUser, user)
    }

    override suspend fun submitEmailVerification(): Task<Void>? {
        return dataSource.submitEmailVerification()
    }
}
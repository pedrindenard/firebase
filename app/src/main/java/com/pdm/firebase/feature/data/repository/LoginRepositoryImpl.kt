package com.pdm.firebase.feature.data.repository

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.domain.datasource.LoginDataSource
import com.pdm.firebase.feature.domain.repository.LoginRepository

class LoginRepositoryImpl(private val dataSource: LoginDataSource) : LoginRepository {

    override suspend fun loginWithUser(email: String, password: String): Task<AuthResult>? {
        return dataSource.loginWithUser(email, password)
    }

    override suspend fun loginWithGoogle(data: Intent): Task<AuthResult>? {
        return dataSource.loginWithGoogle(data)
    }

    override suspend fun loginWithFacebook(data: Intent): Task<AuthResult>? {
        return dataSource.loginWithFacebook(data)
    }

    override suspend fun loginWithGitHub(data: Intent): Task<AuthResult>? {
        return dataSource.loginWithGitBub(data)
    }

    override suspend fun recoveryPassword(email: String): Task<Void>? {
        return dataSource.recoveryPassword(email)
    }
}
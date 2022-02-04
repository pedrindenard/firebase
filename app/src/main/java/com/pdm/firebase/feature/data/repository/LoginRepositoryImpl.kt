package com.pdm.firebase.feature.data.repository

import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import com.pdm.firebase.feature.domain.datasource.LoginDataSource
import com.pdm.firebase.feature.domain.repository.LoginRepository

class LoginRepositoryImpl(private val dataSource: LoginDataSource) : LoginRepository {

    override suspend fun loginWithUser(email: String, password: String): Task<AuthResult>? {
        return dataSource.loginWithUser(email, password)
    }

    override suspend fun loginWithGoogle(accessToken: String): Task<AuthResult>? {
        return dataSource.loginWithGoogle(accessToken)
    }

    override suspend fun loginWithFacebook(accessToken: AccessToken): Task<AuthResult>? {
        return dataSource.loginWithFacebook(accessToken)
    }

    override suspend fun loginWithGitHub(task: Task<AuthResult>): Task<AuthResult>? {
        return dataSource.loginWithGitHub(task)
    }

    override suspend fun loginWithNumberPhone(): Task<AuthResult>? {
        return dataSource.loginWithNumberPhone()
    }

    override suspend fun recoveryPassword(email: String): Task<Void>? {
        return dataSource.recoveryPassword(email)
    }
}
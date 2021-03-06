package com.pdm.firebase.feature.domain.datasource

import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential

interface LoginDataSource {

    suspend fun loginWithUser(email: String, password: String): Task<AuthResult>?

    suspend fun loginWithGoogle(accessToken: String): Task<AuthResult>?

    suspend fun loginWithFacebook(accessToken: AccessToken): Task<AuthResult>?

    suspend fun loginWithGitHub(task: Task<AuthResult>): Task<AuthResult>?

    suspend fun loginWithNumberPhone(credential: PhoneAuthCredential): Task<AuthResult>?

    suspend fun recoveryPassword(email: String): Task<Void>?

}
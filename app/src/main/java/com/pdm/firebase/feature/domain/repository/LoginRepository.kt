package com.pdm.firebase.feature.domain.repository

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginRepository {

    suspend fun loginWithUser(email: String, password: String): Task<AuthResult>?

    suspend fun loginWithGoogle(data: Intent): Task<AuthResult>?

    suspend fun loginWithFacebook(data: Intent): Task<AuthResult>?

    suspend fun loginWithGitHub(data: Intent): Task<AuthResult>?

    suspend fun recoveryPassword(email: String): Task<Void>?

}
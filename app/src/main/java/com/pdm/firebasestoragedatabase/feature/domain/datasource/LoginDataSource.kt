package com.pdm.firebasestoragedatabase.feature.domain.datasource

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.pdm.firebasestoragedatabase.feature.domain.model.User

interface LoginDataSource {

    suspend fun loginWithUser(email: String, password: String): Task<AuthResult>?

    suspend fun loginWithGoogle(data: Intent): Task<AuthResult>?

    suspend fun loginWithFacebook(data: Intent): Task<AuthResult>?

    suspend fun loginWithGitBub(data: Intent): Task<AuthResult>?

    suspend fun recoveryPassword(email: String): Task<Void>?

}
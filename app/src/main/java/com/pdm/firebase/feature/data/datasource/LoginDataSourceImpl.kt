package com.pdm.firebase.feature.data.datasource

import android.util.Log
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.feature.domain.datasource.LoginDataSource
import com.pdm.firebase.util.ERROR_SERVICE
import com.pdm.firebase.util.hashConfig

class LoginDataSourceImpl : LoginDataSource {

    private val firebaseAuth = Firebase.auth

    override suspend fun loginWithUser(email: String, password: String): Task<AuthResult>? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password.hashConfig()!!)
        } catch (e: Exception) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun loginWithGoogle(accessToken: String): Task<AuthResult>? {
        return try {
            firebaseAuth.signInWithCredential(
                GoogleAuthProvider.getCredential(
                    accessToken, null
                )
            )
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun loginWithFacebook(accessToken: AccessToken): Task<AuthResult>? {
        return try {
            firebaseAuth.signInWithCredential(
                FacebookAuthProvider.getCredential(
                    accessToken.token
                )
            )
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun loginWithGitHub(task: Task<AuthResult>): Task<AuthResult>? {
        return try { task } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun loginWithNumberPhone(): Task<AuthResult>? {
        return try {
            firebaseAuth.signInWithCredential(
                FacebookAuthProvider.getCredential(
                    ""
                )
            )
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun recoveryPassword(email: String): Task<Void>? {
        return try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }
}
package com.pdm.firebasestoragedatabase.feature.data.datasource

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.feature.domain.datasource.LoginDataSource
import com.pdm.firebasestoragedatabase.util.ERROR_SERVICE

class LoginDataSourceImpl : LoginDataSource {

    private val firebaseAuth = Firebase.auth

    override suspend fun loginWithUser(email: String, password: String): Task<AuthResult>? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun loginWithGoogle(data: Intent): Task<AuthResult>? {
        return try {
            firebaseAuth.signInWithCredential(
                GoogleAuthProvider.getCredential(
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                        .getResult(ApiException::class.java)
                        .idToken,
                    null
                )
            )
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun loginWithFacebook(data: Intent): Task<AuthResult>? {
        TODO("Not yet implemented")
    }

    override suspend fun loginWithGitBub(data: Intent): Task<AuthResult>? {
        TODO("Not yet implemented")
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
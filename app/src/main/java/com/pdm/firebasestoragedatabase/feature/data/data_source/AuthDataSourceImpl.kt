package com.pdm.firebasestoragedatabase.feature.data.data_source

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GithubAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.feature.domain.data_source.AuthDataSource
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.SignInFragment
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.SignUpFragment
import com.pdm.firebasestoragedatabase.util.*

class AuthDataSourceImpl : AuthDataSource {

    private val firebaseAuth = Firebase.auth

    override suspend fun loginUserAuth(email: String, password: String): Task<AuthResult>? {
        return try {
            when (SignInFragment.AUTHENTICATION) {
                GOOGLE -> {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                }
                FACEBOOK -> {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                }
                GITHUB -> {
                    firebaseAuth.signInWithCredential(GithubAuthProvider.getCredential(GITHUB_TOKEN))
                }
                else -> {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                }
            }
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun registerUserAuth(email: String, password: String): Task<AuthResult>? {
        return try {
            when (SignUpFragment.AUTHENTICATION) {
                GOOGLE -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                }
                FACEBOOK -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                }
                GITHUB -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                }
                else -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                }
            }
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun recoveryUserAuth(email: String): Task<Void>? {
        return try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun editUserAuth(currentUser: FirebaseUser, user: User): Task<Void>? {
        return try {
            FirebaseDatabase.getInstance().getReference(USERS_DATABASE)
                .child(currentUser.uid)
                .setValue(user)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun deleteUserAuth(currentUser: FirebaseUser): Task<Void>? {
        return try {
            currentUser.delete()
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun submitEmailVerification(): Task<Void>? {
        return try {
            firebaseAuth.currentUser?.sendEmailVerification()
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }
}
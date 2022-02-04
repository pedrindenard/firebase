package com.pdm.firebase.feature.data.datasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.feature.domain.datasource.RegisterDataSource
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.util.ERROR_SERVICE
import com.pdm.firebase.util.USERS_DATABASE
import com.pdm.firebase.util.hashConfig

class RegisterDataSourceImpl : RegisterDataSource {

    private val firebaseAuth = Firebase.auth

    override suspend fun registerWithUser(email: String, password: String): Task<AuthResult>? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password.hashConfig()!!)
        } catch (e: Throwable) {
            Log.e(ERROR_SERVICE, e.toString())
            null
        }
    }

    override suspend fun addInfoUser(currentUser: FirebaseUser, user: User): Task<Void>? {
        return try {
            FirebaseDatabase.getInstance().getReference(USERS_DATABASE)
                .child(currentUser.uid)
                .setValue(user)
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
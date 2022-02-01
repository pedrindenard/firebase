package com.pdm.firebasestoragedatabase.feature.data.datasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.pdm.firebasestoragedatabase.feature.domain.datasource.RegisterDataSource
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.util.ERROR_SERVICE
import com.pdm.firebasestoragedatabase.util.USERS_DATABASE

class RegisterDataSourceImpl : RegisterDataSource {

    private val firebaseAuth = Firebase.auth

    override suspend fun registerWithUser(email: String, password: String): Task<AuthResult>? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
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
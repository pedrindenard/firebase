package com.pdm.firebasestoragedatabase.feature.data.fromto

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.model.UserGoogle
import com.pdm.firebasestoragedatabase.util.mapTo

fun Task<DataSnapshot>.fromDataToUser(): User {
    val user = this.result.getValue(User::class.java)

    return User(
        name = user?.name ?: "",
        lastName = user?.lastName ?: "",
        email = user?.email ?: "",
        legalDocument = user?.legalDocument ?: "",
        birthdate = user?.birthdate ?: "",
        picture = user?.picture ?: "",
        gender = user?.gender ?: 0
    )
}

fun Task<AuthResult>.fromDataToUserGoogle(): UserGoogle? {
    return this.result.additionalUserInfo?.profile?.mapTo()
}
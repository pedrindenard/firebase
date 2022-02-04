package com.pdm.firebase.feature.data.fromto

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.model.auth.UserFacebook
import com.pdm.firebase.feature.domain.model.auth.UserGitHub
import com.pdm.firebase.feature.domain.model.auth.UserGoogle
import com.pdm.firebase.util.mapTo

fun Task<DataSnapshot>.fromDataToUser(): User {
    val user = this.result.getValue(User::class.java)

    return User(
        name = user?.name ?: "",
        fullName = user?.fullName ?: "",
        email = user?.email ?: "",
        legalDocument = user?.legalDocument ?: "",
        birthdate = user?.birthdate ?: "",
        picture = user?.picture ?: "",
        gender = user?.gender ?: 0
    )
}

fun Task<AuthResult>.fromDataToUserGoogle(): User? {
    val result = this.result.additionalUserInfo?.profile?.mapTo<UserGoogle>()

    return if (result != null) {
        User(
            name = result.firstName,
            fullName = result.familyName,
            email = result.email,
            legalDocument = "",
            birthdate = "",
            picture = "",
            numberPhone = "",
            gender = 1
        )
    } else null
}

fun Task<AuthResult>.fromDataToUserFacebook(): User? {
    val result = this.result.additionalUserInfo?.profile?.mapTo<UserFacebook>()

    return if (result != null) {
        return User(
            name = result.name,
            fullName = result.lastName,
            email = result.email,
            legalDocument = "",
            birthdate = "",
            picture = result.picture.url,
            numberPhone = "",
            gender = 1
        )
    } else null
}

fun Task<AuthResult>.fromDataToUserGitHub(email: String): User? {
    val result = this.result.additionalUserInfo?.profile?.mapTo<UserGitHub>()

    return if (result != null) {
        return User(
            name = "",
            fullName = result.name,
            email = result.email ?: email,
            legalDocument = "",
            birthdate = "",
            picture = "",
            numberPhone = "",
            gender = 1
        )
    } else null
}
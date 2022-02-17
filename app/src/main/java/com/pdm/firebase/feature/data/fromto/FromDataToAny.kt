package com.pdm.firebase.feature.data.fromto

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.pdm.firebase.arquitecture.Event.Companion.mapTo
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.model.auth.UserFacebook
import com.pdm.firebase.feature.domain.model.auth.UserGitHub
import com.pdm.firebase.feature.domain.model.auth.UserGoogle
import com.pdm.firebase.feature.domain.model.privacy.Privacy

fun Task<DataSnapshot>.fromDataToUser(): User {
    val user = this.result.getValue(User::class.java)

    return User(
        name = user?.name ?: "",
        lastName = user?.lastName ?: "",
        email = user?.email ?: "",
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
            lastName = result.familyName,
            email = result.email,
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
        User(
            name = result.name,
            lastName = result.lastName,
            email = result.email,
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
        User(
            name = result.name.substringAfter(delimiter = "\u0020"),
            lastName = "",
            email = result.email ?: email,
            birthdate = "",
            picture = "",
            numberPhone = "",
            gender = 1
        )
    } else null
}

fun Task<DataSnapshot?>?.fromDataToPrivacy(): Privacy? {
    val result = this?.result?.value?.mapTo<Privacy>()

    return if (result != null) {
        Privacy(
            title = result.title,
            description = result.description,
            paragraph = result.paragraph,
            lastUpdate = result.lastUpdate
        )
    } else null
}
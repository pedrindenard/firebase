package com.pdm.firebase.feature.domain.model.auth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserGoogle (
    @SerializedName(value = "given_name") val firstName: String,
    @SerializedName(value = "family_name") val familyName: String,
    @SerializedName(value = "email") val email: String,
    @SerializedName(value = "picture") val picture: String,
    @SerializedName(value = "email_verified") val emailVerified: Boolean
) : Serializable
package com.pdm.firebase.feature.domain.model.auth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserFacebook(
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "last_name")val lastName: String,
    @SerializedName(value = "first_name")val firstName: String,
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "picture")val picture: UserPicture
) : Serializable
package com.pdm.firebase.feature.domain.model.tv.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreatedBy(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "credit_id") val creditID: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "gender") val gender: Int,
    @SerializedName(value = "profile_path") val profilePath: String
) : Serializable
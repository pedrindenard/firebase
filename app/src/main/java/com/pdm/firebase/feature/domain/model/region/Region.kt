package com.pdm.firebase.feature.domain.model.region

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Region(
    @SerializedName(value = "iso_3166_1") val initials: String,
    @SerializedName(value = "english_name") val englishName: String,
    @SerializedName(value = "native_name") val nativeName: String,
    @SerializedName(value = "is_select") var isSelect: Boolean
) : Serializable

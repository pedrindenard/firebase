package com.pdm.firebase.feature.domain.model.actor

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class Actor(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "adult") val adult: Boolean,
    @SerializedName(value = "gender") val gender: Int,
    @SerializedName(value = "known_for") val knowByMovie: List<Any>,
    @SerializedName(value = "known_for_department") val knowByDepartment: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "popularity") val popularity: BigDecimal,
    @SerializedName(value = "profile_path") val imgProfile: String?
) : Serializable
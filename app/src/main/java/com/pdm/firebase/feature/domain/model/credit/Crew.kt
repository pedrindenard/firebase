package com.pdm.firebase.feature.domain.model.credit

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class Crew (
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "gender") val gender: Int?,
    @SerializedName(value = "known_for_department") val knowForDepart: String,
    @SerializedName(value = "popularity") val popularity: BigDecimal,
    @SerializedName(value = "profile_path") val profile: String?,
    @SerializedName(value = "department") val department: String,
    @SerializedName(value = "job") val job: String,
): Serializable
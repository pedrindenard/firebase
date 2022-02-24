package com.pdm.firebase.feature.domain.model.people.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class PeopleDetailsResponse(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "adult") val adult: Boolean,
    @SerializedName(value = "gender") val gender: Int,
    @SerializedName(value = "known_for_department") val knowByDepartment: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "popularity") val popularity: Float,
    @SerializedName(value = "profile_path") val imgProfile: String?,
    @SerializedName(value = "also_known_as") val knowAs: List<String>?,
    @SerializedName(value = "biography") val overview: String?,
    @SerializedName(value = "birthday") val birthday: String?,
    @SerializedName(value = "place_of_birth") val birthDatePlace: String?
) : Serializable
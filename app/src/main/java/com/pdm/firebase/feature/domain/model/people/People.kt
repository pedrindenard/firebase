package com.pdm.firebase.feature.domain.model.people

import com.google.gson.annotations.SerializedName
import com.pdm.firebase.feature.domain.model.search.Search
import java.io.Serializable

data class People(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "adult") val adult: Boolean,
    @SerializedName(value = "gender") val gender: Int,
    @SerializedName(value = "known_for_department") val knowByDepartment: String,
    @SerializedName(value = "known_for") val knowFor: List<Search>,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "popularity") val popularity: Float,
    @SerializedName(value = "profile_path") val imgProfile: String?
) : Serializable
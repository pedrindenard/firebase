package com.pdm.firebase.feature.domain.model.credit.people

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class PeopleCredits (
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "name") val name: String?,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "media_type") val type: String,
    @SerializedName(value = "poster_path") val background: String?,
    @SerializedName(value = "popularity") val popularity: BigDecimal
): Serializable
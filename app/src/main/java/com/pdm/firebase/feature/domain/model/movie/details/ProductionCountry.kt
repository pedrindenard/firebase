package com.pdm.firebase.feature.domain.model.movie.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductionCountry(
    @SerializedName(value = "iso_3166_1") val iso: String,
    @SerializedName(value = "name") val name: String
) : Serializable
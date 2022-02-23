package com.pdm.firebase.feature.domain.model.movie.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductionCompany(
    @SerializedName(value = "id") val id: Long,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "logo_path") val logo: String?,
    @SerializedName(value = "origin_country") val country: String
) : Serializable
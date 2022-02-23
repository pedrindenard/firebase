package com.pdm.firebase.feature.domain.model.company

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompanyResponse(
    @SerializedName(value = "id") val id: Long,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "logo_path") val logo: String,
    @SerializedName(value = "description") val description: String,
    @SerializedName(value = "headquarters") val headquarters: String,
    @SerializedName(value = "homepage") val homepage: String,
    @SerializedName(value = "origin_country") val country: String
) : Serializable
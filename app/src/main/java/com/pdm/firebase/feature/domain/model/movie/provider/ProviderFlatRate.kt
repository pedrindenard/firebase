package com.pdm.firebase.feature.domain.model.movie.provider

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProviderFlatRate(
    @SerializedName(value = "provider_id") val id: Long,
    @SerializedName(value = "provider_name") val name: String,
    @SerializedName(value = "logo_path") val logo: String
) : Serializable

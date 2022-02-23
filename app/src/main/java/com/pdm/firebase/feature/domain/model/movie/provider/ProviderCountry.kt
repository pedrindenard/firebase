package com.pdm.firebase.feature.domain.model.movie.provider

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProviderCountry(
    @SerializedName(value = "link") val link: String,
    @SerializedName(value = "flatrate") val flatRate: List<ProviderFlatRate>?,
    @SerializedName(value = "buy") val buy: List<ProviderFlatRate>,
    @SerializedName(value = "rent") val rent: List<ProviderFlatRate>
) : Serializable

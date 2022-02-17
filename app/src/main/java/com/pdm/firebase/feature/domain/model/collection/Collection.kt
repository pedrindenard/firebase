package com.pdm.firebase.feature.domain.model.collection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Collection(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "backdrop_path") val background: String?,
    @SerializedName(value = "poster_path") val foreground: String?
) : Serializable
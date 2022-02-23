package com.pdm.firebase.feature.domain.model.image

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName(value = "file_path") val url: String,
    @SerializedName(value = "height") val height: Float,
    @SerializedName(value = "width") val width: Float
) : Serializable
package com.pdm.firebase.feature.domain.model.video

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Video(
    @SerializedName(value = "id") val id: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "key") val key: String,
    @SerializedName(value = "type") val type: String,
    @SerializedName(value = "published_at") val publishedAt: String,
) : Serializable
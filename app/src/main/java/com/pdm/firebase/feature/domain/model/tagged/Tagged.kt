package com.pdm.firebase.feature.domain.model.tagged

import com.google.gson.annotations.SerializedName
import com.pdm.firebase.feature.domain.model.search.Search
import java.io.Serializable

data class Tagged(
    @SerializedName(value = "media") val media: Search,
    @SerializedName(value = "media_type") val mediaType: String
): Serializable
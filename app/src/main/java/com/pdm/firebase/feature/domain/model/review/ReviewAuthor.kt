package com.pdm.firebase.feature.domain.model.review

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewAuthor (
    @SerializedName(value = "avatar_path") val avatar: String?,
    @SerializedName(value = "rating") val rating: Float?,
): Serializable
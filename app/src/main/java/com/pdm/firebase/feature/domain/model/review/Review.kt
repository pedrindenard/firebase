package com.pdm.firebase.feature.domain.model.review

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Review(
    @SerializedName(value = "author") val author: String,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "author_details") val authorDetails: ReviewAuthor,
    @SerializedName(value = "created_at") val createdAt: String,
    @SerializedName(value = "updated_at") val updatedAt: String,
) : Serializable

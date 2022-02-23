package com.pdm.firebase.feature.domain.model.review

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewResponse(
    @SerializedName(value = "id") var id: Int,
    @SerializedName(value = "page") var currentPage: Int,
    @SerializedName(value = "results") val results: MutableList<Review>,
    @SerializedName(value = "total_pages") val totalPage: Int,
    @SerializedName(value = "total_results") val totalResults: Int
) : Serializable

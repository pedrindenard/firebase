package com.pdm.firebase.feature.domain.model.actor

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActorsResponse(
    @SerializedName(value = "page") val currentPage: Int,
    @SerializedName(value = "results") val results: MutableList<Actor>,
    @SerializedName(value = "total_pages") val totalPage: Int,
    @SerializedName(value = "total_results") val totalResults: Int
) : Serializable

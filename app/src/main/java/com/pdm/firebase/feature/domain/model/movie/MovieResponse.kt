package com.pdm.firebase.feature.domain.model.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponse(
    @SerializedName(value = "page") var currentPage: Int,
    @SerializedName(value = "results") val results: MutableList<Movie>,
    @SerializedName(value = "total_pages") val totalPage: Int,
    @SerializedName(value = "total_results") val totalResults: Int
) : Serializable


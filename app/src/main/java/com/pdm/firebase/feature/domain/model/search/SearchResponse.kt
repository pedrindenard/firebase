package com.pdm.firebase.feature.domain.model.search

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResponse(
    @SerializedName(value = "page") var currentPage: Int,
    @SerializedName(value = "results") val results: MutableList<Search>,
    @SerializedName(value = "total_pages") val totalPage: Int,
    @SerializedName(value = "total_results") val totalResults: Int
) : Serializable
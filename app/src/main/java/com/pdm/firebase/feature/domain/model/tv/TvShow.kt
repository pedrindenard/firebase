package com.pdm.firebase.feature.domain.model.tv

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class TvShow(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "poster_path") val foreground: String?,
    @SerializedName(value = "popularity") val popularity: BigDecimal,
    @SerializedName(value = "overview") val description: String,
    @SerializedName(value = "backdrop_path") val background: String?,
    @SerializedName(value = "vote_average") val voteAverage: Float,
    @SerializedName(value = "media_type") val type: String,
    @SerializedName(value = "first_air_date") val releaseDate: String,
    @SerializedName(value = "origin_country") val countries: List<String>,
    @SerializedName(value = "genre_ids") val genres: List<Int>,
    @SerializedName(value = "original_language") val language: String,
    @SerializedName(value = "vote_count") val voteCount: Float,
    @SerializedName(value = "original_name") val originalName: String
) : Serializable
package com.pdm.firebase.feature.domain.model.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class Movie(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "adult") val isToAdult: Boolean,
    @SerializedName(value = "backdrop_path") val imgBackground: String?,
    @SerializedName(value = "poster_path") val imgForeground: String?,
    @SerializedName(value = "genre_ids") val genres: List<Int>,
    @SerializedName(value = "original_title") val originalTitle: String,
    @SerializedName(value = "original_language") val originalLanguage: String,
    @SerializedName(value = "overview") val overview: String,
    @SerializedName(value = "popularity") val popularity: BigDecimal,
    @SerializedName(value = "release_date") val releaseDate: String,
    @SerializedName(value = "video") val videoAvailable: Boolean,
    @SerializedName(value = "vote_average") val averageVote: Float,
    @SerializedName(value = "vote_count") val countVote: Int
) : Serializable
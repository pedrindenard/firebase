package com.pdm.firebase.feature.domain.model.movie.details

import com.google.gson.annotations.SerializedName
import com.pdm.firebase.feature.domain.model.gender.Gender
import java.io.Serializable

data class MovieDetailsResponse(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "original_title") val originalTitle: String,
    @SerializedName(value = "adult") val adult: Boolean,
    @SerializedName(value = "backdrop_path") val background: String?,
    @SerializedName(value = "belongs_to_collection") val collections: Any?,
    @SerializedName(value = "budget") val budget: Float,
    @SerializedName(value = "genres") val genres: List<Gender>,
    @SerializedName(value = "homepage") val homepage: String?,
    @SerializedName(value = "overview") val overview: String?,
    @SerializedName(value = "popularity") val popularity: Float,
    @SerializedName(value = "poster_path") val foreground: String?,
    @SerializedName(value = "production_companies") val companies: List<ProductionCompany>,
    @SerializedName(value = "production_countries") val countries: List<ProductionCountry>,
    @SerializedName(value = "spoken_languages") val languages: List<SpokenLanguage>,
    @SerializedName(value = "release_date") val releaseDate: String,
    @SerializedName(value = "revenue") val revenue: Float,
    @SerializedName(value = "runtime") val runtime: Float?,
    @SerializedName(value = "tagline") val slogan: String,
    @SerializedName(value = "vote_average") val voteAverage: Float,
    @SerializedName(value = "vote_count") val voteCount: Float
) : Serializable
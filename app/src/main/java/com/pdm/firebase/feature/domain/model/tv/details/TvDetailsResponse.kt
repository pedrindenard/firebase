package com.pdm.firebase.feature.domain.model.tv.details

import com.google.gson.annotations.SerializedName
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCompany
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCountry
import com.pdm.firebase.feature.domain.model.movie.details.SpokenLanguage
import java.io.Serializable

data class TvDetailsResponse(
    @SerializedName(value = "backdrop_path") val backdropPath: String,
    @SerializedName(value = "created_by") val createdBy: List<CreatedBy>,
    @SerializedName(value = "episode_run_time") val runtime: List<Int>?,
    @SerializedName(value = "first_air_date") val firstAirDate: String,
    @SerializedName(value = "genres") val genres: List<Gender>,
    @SerializedName(value = "homepage") val homepage: String?,
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "in_production") val inProduction: Boolean,
    @SerializedName(value = "languages") val languages: List<String>,
    @SerializedName(value = "last_air_date") val lastAirDate: String?,
    @SerializedName(value = "last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAir?,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "next_episode_to_air") val nextEpisodeToAir: LastEpisodeToAir?,
    @SerializedName(value = "networks") val networks: List<Network>,
    @SerializedName(value = "number_of_episodes") val numberOfEpisodes: Int?,
    @SerializedName(value = "number_of_seasons") val numberOfSeasons: Int,
    @SerializedName(value = "origin_country") val originCountry: List<String>,
    @SerializedName(value = "original_language") val originalLanguage: String,
    @SerializedName(value = "original_name") val originalName: String,
    @SerializedName(value = "overview") val overview: String?,
    @SerializedName(value = "popularity") val popularity: Double,
    @SerializedName(value = "poster_path") val posterPath: String,
    @SerializedName(value = "production_companies") val companies: List<ProductionCompany>,
    @SerializedName(value = "production_countries") val countries: List<ProductionCountry>,
    @SerializedName(value = "seasons") val seasons: List<Season>,
    @SerializedName(value = "spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @SerializedName(value = "status") val status: String,
    @SerializedName(value = "tagline") val tagline: String?,
    @SerializedName(value = "type") val type: String,
    @SerializedName(value = "vote_average") val voteAverage: Double,
    @SerializedName(value = "vote_count") val voteCount: Double
) : Serializable
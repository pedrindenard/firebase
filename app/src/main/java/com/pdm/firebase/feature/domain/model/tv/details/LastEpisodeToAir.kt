package com.pdm.firebase.feature.domain.model.tv.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LastEpisodeToAir(
    @SerializedName(value = "air_date") val airDate: String,
    @SerializedName(value = "episode_number") val episodeNumber: Long,
    @SerializedName(value = "id") val id: Long,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "overview") val overview: String,
    @SerializedName(value = "production_code") val productionCode: String,
    @SerializedName(value = "season_number") val seasonNumber: Long,
    @SerializedName(value = "still_path") val stillPath: String,
    @SerializedName(value = "vote_average") val voteAverage: Double,
    @SerializedName(value = "vote_count") val voteCount: Long
) : Serializable
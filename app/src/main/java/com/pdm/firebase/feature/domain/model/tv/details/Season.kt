package com.pdm.firebase.feature.domain.model.tv.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Season(
    @SerializedName(value = "air_date") val airDate: String?,
    @SerializedName(value = "episode_count") val episodeCount: Long,
    @SerializedName(value = "id") val id: Long,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "overview") val overview: String,
    @SerializedName(value = "poster_path") val posterPath: String,
    @SerializedName(value = "season_number") val seasonNumber: Long
) : Serializable
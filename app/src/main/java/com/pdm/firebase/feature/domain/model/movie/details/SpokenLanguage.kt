package com.pdm.firebase.feature.domain.model.movie.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpokenLanguage(
    @SerializedName(value = "english_name") val englishName: String,
    @SerializedName(value = "iso_639_1") val iso: String,
    @SerializedName(value = "name") val name: String
) : Serializable
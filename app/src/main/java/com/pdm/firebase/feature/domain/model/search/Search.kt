package com.pdm.firebase.feature.domain.model.search

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class Search(
    @SerializedName(value = "id") val id: Int, //Movie, Tv, People, Collection

    @SerializedName(value = "popularity") val popularity: BigDecimal, //Movie, Tv, People
    @SerializedName(value = "media_type") val mediaType: String, //Movie, Tv, People

    @SerializedName(value = "backdrop_path") val backdropPath: String?, //Movie, Tv, Collection
    @SerializedName(value = "poster_path") val posterPath: String?, //Movie, Tv, Collection

    @SerializedName(value = "adult") val adult: Boolean?, //Movie, Tv
    @SerializedName(value = "overview") val overview: String?, //Movie, Tv
    @SerializedName(value = "genre_ids") val genreIds: List<Int>?, //Movie, Tv
    @SerializedName(value = "original_language") val originalLanguage: String?, //Movie, Tv
    @SerializedName(value = "vote_average") val voteAverage: Float?, //Movie, Tv
    @SerializedName(value = "vote_count") val voteCount: Int?, //Movie, Tv

    @SerializedName(value = "release_date") val releaseDate: String?, //Movie
    @SerializedName(value = "original_title") val originalTitle: String?, //Movie
    @SerializedName(value = "title") val title: String?, //Movie
    @SerializedName(value = "video") val video: Boolean?, //Movie

    @SerializedName(value = "first_air_date") val firstAirDate: String?, //Tv
    @SerializedName(value = "origin_country") val originalCountry: List<String>?, //Tv
    @SerializedName(value = "original_name") val originalName: String?, //Tv

    @SerializedName(value = "name") val name: String?, //People, Collection

    @SerializedName(value = "known_for") val knownFor: List<Any>?, //People
    @SerializedName(value = "known_for_department") val knownForDepartment: String?, //People
    @SerializedName(value = "profile_path") val profilePath: String?, //People
    @SerializedName(value = "gender") val gender: Int? //People
) : Serializable
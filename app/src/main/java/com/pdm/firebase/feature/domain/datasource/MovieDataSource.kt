package com.pdm.firebase.feature.domain.datasource

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse

interface MovieDataSource {

    suspend fun getSuperBanner(): Resource<MovieResponse?>

    suspend fun getPopularMovie(): Resource<MovieResponse?>

    suspend fun getRatedMovie(): Resource<MovieResponse?>

    suspend fun getGendersMovie(): Resource<GenderResponse?>

    suspend fun getGendersTv(): Resource<GenderResponse?>

    suspend fun getMovieByGender(id: Int): Resource<MovieResponse?>

    suspend fun getUpcomingMovie(): Resource<MovieResponse?>

    suspend fun getMovieNowPlaying(page: Int): Resource<MovieResponse?>

    suspend fun getBestActors(): Resource<ActorsResponse?>

}
package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse

interface MovieRepository {

    suspend fun getSuperBanner(refresh: Boolean): Resource<MovieResponse?>

    suspend fun getPopularMovie(refresh: Boolean): Resource<MovieResponse?>

    suspend fun getRatedMovie(refresh: Boolean): Resource<MovieResponse?>

    suspend fun getGendersMovie(refresh: Boolean): Resource<GenderResponse?>

    suspend fun getGendersTv(refresh: Boolean): Resource<GenderResponse?>

    suspend fun getMovieByGender(id: Int, refresh: Boolean): Resource<MovieResponse?>

    suspend fun getUpcomingMovie(refresh: Boolean): Resource<MovieResponse?>

    suspend fun getMovieNowPlaying(page: Int): Resource<MovieResponse?>

    suspend fun getBestActors(refresh: Boolean): Resource<ActorsResponse?>

}
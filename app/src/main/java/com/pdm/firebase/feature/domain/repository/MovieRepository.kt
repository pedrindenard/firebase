package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse

interface MovieRepository {

    suspend fun getPopularMovie(page: Int, ignoreCache: Boolean): Resource<MovieResponse?>

    suspend fun getRatedMovie(page: Int, ignoreCache: Boolean): Resource<MovieResponse?>

    suspend fun getGendersMovie(ignoreCache: Boolean): Resource<GenderResponse?>

    suspend fun getMovieByGender(page: Int, id: Int, ignoreCache: Boolean): Resource<MovieResponse?>

    suspend fun getUpcomingMovie(page: Int, ignoreCache: Boolean): Resource<MovieResponse?>

    suspend fun getMovieNowPlaying(page: Int, ignoreCache: Boolean): Resource<MovieResponse?>

}
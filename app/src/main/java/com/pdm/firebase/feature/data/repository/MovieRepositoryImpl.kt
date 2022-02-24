package com.pdm.firebase.feature.data.repository

import com.google.gson.Gson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.GENDERS_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.MOVIE_BY_GENDER
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.NOW_PLAYING_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.POPULAR_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.RATED_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.UPCOMING_MOVIE
import com.pdm.firebase.feature.domain.datasource.MovieDataSource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val dataSource: MovieDataSource,
    private val cache: CacheImpl
) : MovieRepository {

    override suspend fun getPopularMovie(page: Int, ignoreCache: Boolean): Resource<MovieResponse?> {
        return when {
            cache.get(POPULAR_MOVIE).isEmpty() || ignoreCache -> {
                dataSource.getPopularMovie(page = page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(POPULAR_MOVIE),
                        MovieResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getRatedMovie(page: Int, ignoreCache: Boolean): Resource<MovieResponse?> {
        return when {
            cache.get(RATED_MOVIE).isEmpty() || ignoreCache -> {
                dataSource.getRatedMovie(page = page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(RATED_MOVIE),
                        MovieResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getGendersMovie(ignoreCache: Boolean): Resource<GenderResponse?> {
        return when {
            cache.get(GENDERS_MOVIE).isEmpty() || ignoreCache -> {
                dataSource.getGendersMovie()
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(GENDERS_MOVIE),
                        GenderResponse::class.java
                    )
                )
            }
        }
    }



    override suspend fun getMovieByGender(page: Int, id: Int, ignoreCache: Boolean): Resource<MovieResponse?> {
        return when (ignoreCache) {
            true -> {
                dataSource.getMovieByGender(
                    page = page,
                    id = id
                )
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(MOVIE_BY_GENDER),
                        MovieResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getUpcomingMovie(page: Int, ignoreCache: Boolean): Resource<MovieResponse?> {
        return when {
            cache.get(UPCOMING_MOVIE).isEmpty() || ignoreCache -> {
                dataSource.getUpcomingMovie(page = page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(UPCOMING_MOVIE),
                        MovieResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getMovieNowPlaying(page: Int, ignoreCache: Boolean): Resource<MovieResponse?> {
        return when {
            cache.get(NOW_PLAYING_MOVIE).isEmpty() || ignoreCache -> {
                dataSource.getMovieNowPlaying(page = page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(NOW_PLAYING_MOVIE),
                        MovieResponse::class.java
                    )
                )
            }
        }
    }
}
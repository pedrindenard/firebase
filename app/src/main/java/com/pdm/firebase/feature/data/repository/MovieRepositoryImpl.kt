package com.pdm.firebase.feature.data.repository

import com.google.gson.Gson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.BEST_ACTORS
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.GENDERS_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.GENDERS_TV
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.HOME_BANNER
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.NOW_PLAYING_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.POPULAR_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.RATED_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.UPCOMING_MOVIE
import com.pdm.firebase.feature.domain.datasource.MovieDataSource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val dataSource: MovieDataSource,
    private val cache: CacheImpl
) : MovieRepository {

    override suspend fun getSuperBanner(refresh: Boolean): Resource<MovieResponse?> {
        return when {
            refresh -> {
                dataSource.getSuperBanner()
            }
            cache.get(HOME_BANNER).isEmpty() -> {
                dataSource.getSuperBanner()
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(HOME_BANNER),
                        MovieResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getPopularMovie(refresh: Boolean): Resource<MovieResponse?> {
        return when {
            refresh -> {
                dataSource.getPopularMovie()
            }
            cache.get(POPULAR_MOVIE).isEmpty() -> {
                dataSource.getPopularMovie()
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

    override suspend fun getRatedMovie(refresh: Boolean): Resource<MovieResponse?> {
        return when {
            refresh -> {
                dataSource.getRatedMovie()
            }
            cache.get(RATED_MOVIE).isEmpty() -> {
                dataSource.getRatedMovie()
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

    override suspend fun getGendersMovie(refresh: Boolean): Resource<GenderResponse?> {
        return when {
            refresh -> {
                dataSource.getGendersMovie()
            }
            cache.get(GENDERS_MOVIE).isEmpty() -> {
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

    override suspend fun getGendersTv(refresh: Boolean): Resource<GenderResponse?> {
        return when {
            refresh -> {
                dataSource.getGendersTv()
            }
            cache.get(GENDERS_TV).isEmpty() -> {
                dataSource.getGendersTv()
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(GENDERS_TV),
                        GenderResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getMovieByGender(id: Int, refresh: Boolean): Resource<MovieResponse?> {
        return when (refresh) {
            true -> {
                dataSource.getMovieByGender(
                    id = id
                )
            }
            else -> {
                dataSource.getMovieByGender(
                    id = id
                )
            }
        }
    }

    override suspend fun getUpcomingMovie(refresh: Boolean): Resource<MovieResponse?> {
        return when {
            cache.get(UPCOMING_MOVIE).isEmpty() -> {
                dataSource.getUpcomingMovie()
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

    override suspend fun getMovieNowPlaying(page: Int, refresh: Boolean): Resource<MovieResponse?> {
        return when {
            cache.get(NOW_PLAYING_MOVIE).isEmpty() -> {
                dataSource.getMovieNowPlaying(page)
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

    override suspend fun getBestActors(refresh: Boolean): Resource<ActorsResponse?> {
        return when {
            refresh -> {
                dataSource.getBestActors()
            }
            cache.get(BEST_ACTORS).isEmpty() -> {
                dataSource.getBestActors()
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(BEST_ACTORS),
                        ActorsResponse::class.java
                    )
                )
            }
        }
    }
}
package com.pdm.firebase.feature.data.repository

import com.google.gson.Gson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.POPULAR_TV
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.TOP_RATED_TV
import com.pdm.firebase.feature.domain.datasource.TvShowDataSource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.repository.TvShowRepository

class TvShowRepositoryImpl(
    private val dataSource: TvShowDataSource,
    private val cache: CacheImpl
) : TvShowRepository {

    override suspend fun getTvShowPopular(page: Int, ignoreCache: Boolean): Resource<TvShowResponse?> {
        return when {
            cache.get(POPULAR_TV).isEmpty() || ignoreCache -> {
                dataSource.getTvShowPopular(page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(POPULAR_TV),
                        TvShowResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getGendersTv(ignoreCache: Boolean): Resource<GenderResponse?> {
        return when {
            cache.get(CacheImpl.GENDERS_TV).isEmpty() || ignoreCache -> {
                dataSource.getGendersTv()
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(CacheImpl.GENDERS_TV),
                        GenderResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getTvShowByGender(page: Int, id: Int, ignoreCache: Boolean): Resource<TvShowResponse?> {
        return when (ignoreCache) {
            true -> {
                dataSource.getTvShowByGender(
                    page = page,
                    id = id
                )
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(CacheImpl.MOVIE_BY_GENDER),
                        TvShowResponse::class.java
                    )
                )
            }
        }
    }

    override suspend fun getTvShowTopRated(page: Int, ignoreCache: Boolean): Resource<TvShowResponse?> {
        return when {
            cache.get(TOP_RATED_TV).isEmpty() || ignoreCache -> {
                dataSource.getTvShowPopular(page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(TOP_RATED_TV),
                        TvShowResponse::class.java
                    )
                )
            }
        }
    }
}
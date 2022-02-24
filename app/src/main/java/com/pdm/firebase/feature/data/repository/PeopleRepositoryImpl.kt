package com.pdm.firebase.feature.data.repository

import com.google.gson.Gson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.BEST_ACTORS
import com.pdm.firebase.feature.domain.datasource.PeopleDataSource
import com.pdm.firebase.feature.domain.model.people.PeopleResponse
import com.pdm.firebase.feature.domain.repository.PeopleRepository

class PeopleRepositoryImpl(
    private val dataSource: PeopleDataSource,
    private val cache: CacheImpl
) : PeopleRepository {

    override suspend fun getBestActors(page: Int, ignoreCache: Boolean): Resource<PeopleResponse?> {
        return when {
            cache.get(BEST_ACTORS).isEmpty() || ignoreCache -> {
                dataSource.getBestActors(page = page)
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(BEST_ACTORS),
                        PeopleResponse::class.java
                    )
                )
            }
        }
    }
}
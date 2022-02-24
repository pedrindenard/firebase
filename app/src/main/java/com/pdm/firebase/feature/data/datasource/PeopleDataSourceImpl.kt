package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Event.Companion.toJson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.BEST_ACTORS
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.PeopleDataSource
import com.pdm.firebase.feature.domain.model.people.PeopleResponse

class PeopleDataSourceImpl(private val api: Api, private val cache: CacheImpl) : PeopleDataSource {

    override suspend fun getBestActors(page: Int): Resource<PeopleResponse?> {
        return safeCallApi {
            val response = api.getBestActors(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(BEST_ACTORS, it.toJson())
                        })
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }
}
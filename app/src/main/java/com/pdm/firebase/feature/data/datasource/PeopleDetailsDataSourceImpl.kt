package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event
import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.PeopleDetailsDataSource
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.people.details.PeopleDetailsResponse
import com.pdm.firebase.feature.domain.model.tagged.TaggedResponse

class PeopleDetailsDataSourceImpl(private val api: Api) : PeopleDetailsDataSource {

    override suspend fun getPeopleImages(id: Int): Resource<ImageResponse?> {
        return Event.safeCallApi {
            val response = api.getPeopleImages(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
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

    override suspend fun getPeopleDetails(id: Int): Resource<PeopleDetailsResponse?> {
        return Event.safeCallApi {
            val response = api.getPeopleDetails(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
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

    override suspend fun getPeopleCredits(id: Int): Resource<PeopleCreditsResponse?> {
        return Event.safeCallApi {
            val response = api.getPeopleCredits(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
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

    override suspend fun getPeopleTagged(id: Int, page: Int): Resource<TaggedResponse?> {
        return Event.safeCallApi {
            val response = api.getPeopleTagged(id = id, page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
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
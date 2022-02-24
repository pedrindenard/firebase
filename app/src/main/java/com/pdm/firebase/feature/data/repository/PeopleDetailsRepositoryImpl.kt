package com.pdm.firebase.feature.data.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.datasource.PeopleDetailsDataSource
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.people.details.PeopleDetailsResponse
import com.pdm.firebase.feature.domain.model.tagged.TaggedResponse
import com.pdm.firebase.feature.domain.repository.PeopleDetailsRepository

class PeopleDetailsRepositoryImpl(private val dataSource: PeopleDetailsDataSource) : PeopleDetailsRepository {

    override suspend fun getPeopleImages(id: Int): Resource<ImageResponse?> {
        return dataSource.getPeopleImages(id)
    }

    override suspend fun getPeopleDetails(id: Int): Resource<PeopleDetailsResponse?> {
        return dataSource.getPeopleDetails(id)
    }

    override suspend fun getPeopleCredits(id: Int): Resource<PeopleCreditsResponse?> {
        return dataSource.getPeopleCredits(id)
    }

    override suspend fun getPeopleTagged(id: Int, page: Int): Resource<TaggedResponse?> {
        return dataSource.getPeopleTagged(id, page)
    }
}
package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.people.details.PeopleDetailsResponse
import com.pdm.firebase.feature.domain.model.tagged.TaggedResponse

interface PeopleDetailsRepository {

    suspend fun getPeopleImages(id: Int): Resource<ImageResponse?>

    suspend fun getPeopleDetails(id: Int): Resource<PeopleDetailsResponse?>

    suspend fun getPeopleCredits(id: Int): Resource<PeopleCreditsResponse?>

    suspend fun getPeopleTagged(id: Int, page: Int): Resource<TaggedResponse?>
}
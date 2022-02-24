package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.people.PeopleResponse

interface PeopleRepository {

    suspend fun getBestActors(page: Int, ignoreCache: Boolean): Resource<PeopleResponse?>

}
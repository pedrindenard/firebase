package com.pdm.firebase.feature.domain.datasource

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.people.PeopleResponse

interface PeopleDataSource {

    suspend fun getBestActors(page: Int): Resource<PeopleResponse?>

}
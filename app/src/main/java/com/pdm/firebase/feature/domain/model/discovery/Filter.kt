package com.pdm.firebase.feature.domain.model.discovery

import com.pdm.firebase.feature.domain.model.gender.Gender

data class Filter(
    val id: Int,
    val sort: String = "popularity.desc",
    val genre: Gender
)
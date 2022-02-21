package com.pdm.firebase.feature.domain.model.discovery

import com.pdm.firebase.feature.domain.model.gender.Gender

data class Filter(
    var id: Int,
    var sort: String = "popularity.desc",
    val genre: Gender,
    val genreType: String
)
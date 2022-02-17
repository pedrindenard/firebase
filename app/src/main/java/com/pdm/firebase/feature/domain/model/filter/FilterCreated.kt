package com.pdm.firebase.feature.domain.model.filter

import java.io.Serializable

data class FilterCreated(
    val region: String?,
    val releaseDate: Float?,
    val adultContent: Boolean?,
    val category: Int
) : Serializable
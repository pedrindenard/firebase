package com.pdm.firebase.feature.domain.model.credit.movie

import java.io.Serializable

data class MovieCreditsResponse(
    val id: Int,
    val cast: List<MovieCredits>,
    val crew: List<MovieCredits>
) : Serializable
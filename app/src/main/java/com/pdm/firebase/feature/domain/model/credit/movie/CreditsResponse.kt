package com.pdm.firebase.feature.domain.model.credit.movie

import java.io.Serializable

data class CreditsResponse(
    val id: Int,
    val cast: List<Credit>,
    val crew: List<Credit>
) : Serializable
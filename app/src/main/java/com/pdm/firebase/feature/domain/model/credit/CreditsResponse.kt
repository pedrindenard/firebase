package com.pdm.firebase.feature.domain.model.credit

import java.io.Serializable

data class CreditsResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
) : Serializable
package com.pdm.firebase.feature.domain.model.credit.people

import java.io.Serializable

data class PeopleCreditsResponse(
    val id: Int,
    val cast: List<PeopleCredits>,
    val crew: List<PeopleCredits>
) : Serializable
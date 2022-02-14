package com.pdm.firebase.feature.domain.model.gender

import java.io.Serializable

data class GenderResponse(
    val genres: List<Gender>
) : Serializable
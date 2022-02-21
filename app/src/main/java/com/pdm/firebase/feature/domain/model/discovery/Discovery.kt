package com.pdm.firebase.feature.domain.model.discovery

import com.pdm.firebase.feature.domain.model.gender.Gender
import java.io.Serializable

data class Discovery(
    val gender: Gender,
    val param: String
) : Serializable
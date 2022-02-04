package com.pdm.firebase.feature.domain.model.privacy

import java.io.Serializable

data class Content(
    val subTitle: String,
    val description: List<String>
) : Serializable

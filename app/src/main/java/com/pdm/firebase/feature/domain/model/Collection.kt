package com.pdm.firebase.feature.domain.model

import java.io.Serializable

data class Collection(
    val name: String = "",
    val description: String = "",
    val image: String = ""
) : Serializable

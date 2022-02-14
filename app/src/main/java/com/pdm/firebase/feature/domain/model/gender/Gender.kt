package com.pdm.firebase.feature.domain.model.gender

import java.io.Serializable

data class Gender(
    val id: Int,
    val name: String,
    var image: String? = null,
    var isSelect: Boolean = false
) : Serializable

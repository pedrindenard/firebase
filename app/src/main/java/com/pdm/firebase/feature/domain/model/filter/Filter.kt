package com.pdm.firebase.feature.domain.model.filter

import java.io.Serializable

data class Filter(
    val name: String,
    val param: Int,
    var isSelect: Boolean
) : Serializable
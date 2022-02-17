package com.pdm.firebase.feature.domain.model.filter

object FilterResponse {
    val available = listOf(
        Filter(name = "Geral", param = 1, isSelect = true),
        Filter(name = "Coleção", param = 2, isSelect = false),
        Filter(name = "Pessoa", param = 3, isSelect = false),
        Filter(name = "Cinema", param = 4, isSelect = false),
        Filter(name = "Tv", param = 5, isSelect = false)
    )
}
package com.pdm.firebase.feature.domain.model.filter

object FilterSort {
    val available = listOf(
        Pair("Populares","popularity.desc"),
        Pair("Lançamento","release_date.desc"),
        Pair("Nome(inglês)","original_title.desc"),
        Pair("Avaliações","vote_average.desc"),
        Pair("Bilheteria","revenue.desc")
    )
}
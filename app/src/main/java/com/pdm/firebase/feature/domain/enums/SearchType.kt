package com.pdm.firebase.feature.domain.enums

enum class SearchType(val value: Int) {
    SEARCH_MULTI(value = 1),
    SEARCH_COLLECTION(value = 2),
    SEARCH_PEOPLE(value = 3),
    SEARCH_MOVIE(value = 4),
    SEARCH_TV(value = 5),
    SEARCH_NOW_PLAYING(value = 6)
}
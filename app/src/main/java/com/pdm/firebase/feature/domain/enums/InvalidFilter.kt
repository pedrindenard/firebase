package com.pdm.firebase.feature.domain.enums

enum class InvalidFilter(val value: String) {
    INVALID_YEAR(value = "invalid.year"),
    INVALID_REGION(value = "invalid.region"),
    EMPTY_FILTER(value = "empty.filter")
}
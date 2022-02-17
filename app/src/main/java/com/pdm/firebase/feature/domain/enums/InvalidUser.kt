package com.pdm.firebase.feature.domain.enums

enum class InvalidUser(val value: String) {
    INVALID_UID(value = "invalid.uid"),
    INVALID_EMAIL(value = "invalid.email "),
    CURRENT_USER_IS_NULL(value = "current.user.is.null ")
}
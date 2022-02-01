package com.pdm.firebasestoragedatabase.feature.domain.exceptions

class ProfileException(message: String) : Exception(message)

enum class InvalidUser(val value: String) {
    INVALID_UID("invalid.uid"),
    INVALID_EMAIL("invalid.email "),
    CURRENT_USER_IS_NULL("current.user.is.null ")
}
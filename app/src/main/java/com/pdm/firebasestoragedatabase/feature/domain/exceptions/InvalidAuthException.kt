package com.pdm.firebasestoragedatabase.feature.domain.exceptions

class InvalidAuthException(message: String) : Exception(message)

enum class InvalidAuthFields(val exception: String) {
    EMPTY_NAME("empty.name"),
    EMPTY_EMAIL("empty.email"),
    EMPTY_PASSWORD("empty.password"),
    EMPTY_BIRTHDATE("empty.birthdate"),

    INVALID_EMAIL("invalid.email"),
    INVALID_PASSWORD("invalid.password"),
    INVALID_BIRTHDATE("invalid.birthdate"),

    CURRENT_USER_IS_NULL("current.user.is.null")
}
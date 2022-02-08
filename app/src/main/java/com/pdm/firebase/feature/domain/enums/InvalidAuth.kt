package com.pdm.firebase.feature.domain.enums

enum class InvalidAuth(val value: String) {
    EMPTY_NAME("empty.name "),
    EMPTY_LAST_NAME("empty.last.name "),

    INVALID_EMAIL("invalid.email "),
    INVALID_BIRTHDATE("invalid.birthdate "),
    INVALID_PASSWORD("invalid.password "),
    INVALID_CONFIRM_PASSWORD("invalid.confirm.password "),
    INVALID_NUMBER_PHONE("invalid.number.phone"),

    SIGN_IN_FAILED("sign.in.failed"),

    CURRENT_USER_IS_NULL("current.user.is.null ")
}
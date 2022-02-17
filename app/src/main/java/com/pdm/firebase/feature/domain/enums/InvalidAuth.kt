package com.pdm.firebase.feature.domain.enums

enum class InvalidAuth(val value: String) {
    EMPTY_NAME(value = "empty.name "),
    EMPTY_LAST_NAME(value = "empty.last.name "),

    INVALID_EMAIL(value = "invalid.email "),
    INVALID_BIRTHDATE(value = "invalid.birthdate "),
    INVALID_PASSWORD(value = "invalid.password "),
    INVALID_GENDER(value = "invalid.gender"),
    INVALID_CONFIRM_PASSWORD(value = "invalid.confirm.password "),
    INVALID_NUMBER_PHONE(value = "invalid.number.phone"),

    SIGN_IN_FAILED(value = "sign.in.failed"),

    CURRENT_USER_IS_NULL(value = "current.user.is.null ")
}
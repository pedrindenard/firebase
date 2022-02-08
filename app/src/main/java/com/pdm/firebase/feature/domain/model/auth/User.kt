package com.pdm.firebase.feature.domain.model.auth

import java.io.Serializable

data class User (
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val birthdate: String = "",
    val picture: String = "",
    val numberPhone: String = "",
    val gender: Int = 0
) : Serializable
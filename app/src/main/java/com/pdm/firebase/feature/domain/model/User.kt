package com.pdm.firebase.feature.domain.model

import java.io.Serializable

data class User (
    val name: String = "",
    val fullName: String = "",
    val email: String = "",
    val legalDocument: String = "",
    val birthdate: String = "",
    val picture: String = "",
    val gender: Int = 0
) : Serializable
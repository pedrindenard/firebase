package com.pdm.firebasestoragedatabase.feature.domain.model

import java.io.Serializable

data class User (
    val name: String,
    val email: String,
    val age: String
) : Serializable
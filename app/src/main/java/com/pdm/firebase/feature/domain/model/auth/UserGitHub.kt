package com.pdm.firebase.feature.domain.model.auth

import java.io.Serializable

data class UserGitHub(
    val name: String,
    val email: String?
) : Serializable
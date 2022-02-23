package com.pdm.firebase.feature.domain.model.movie.provider

import java.io.Serializable

data class ProviderResponse(
    val id: Int,
    val results: Provider
) : Serializable

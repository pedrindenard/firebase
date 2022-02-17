package com.pdm.firebase.feature.domain.model.region

import java.io.Serializable

data class RegionResponse(
    val results: List<Region>
) : Serializable
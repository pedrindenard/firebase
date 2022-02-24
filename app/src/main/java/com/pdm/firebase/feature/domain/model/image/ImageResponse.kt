package com.pdm.firebase.feature.domain.model.image

import java.io.Serializable

data class ImageResponse(
    val id: Int,
    val backdrops: List<Image>?,
    val profiles: List<Image>?
) : Serializable
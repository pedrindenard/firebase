package com.pdm.firebase.feature.domain.model.video

import java.io.Serializable

data class VideoMedia(
    val mediaPosition: Int? = 0,
    val mediaType: String,
    val mediaId: Int
) : Serializable
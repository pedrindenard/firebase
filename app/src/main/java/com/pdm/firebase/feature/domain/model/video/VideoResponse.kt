package com.pdm.firebase.feature.domain.model.video

import java.io.Serializable

data class VideoResponse(
    val id: Int,
    val results: List<Video>
) : Serializable
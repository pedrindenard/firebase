package com.pdm.firebase.feature.domain.model.privacy

import java.io.Serializable

data class Paragraph(
    val title: String,
    val content: List<Content>
) : Serializable

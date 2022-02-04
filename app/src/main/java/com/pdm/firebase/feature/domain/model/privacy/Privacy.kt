package com.pdm.firebase.feature.domain.model.privacy

import java.io.Serializable

data class Privacy(
    val title: String = "",
    val description: List<String> = listOf(),
    val paragraph: List<Paragraph> = listOf(),
    val lastUpdate: String = ""
) : Serializable

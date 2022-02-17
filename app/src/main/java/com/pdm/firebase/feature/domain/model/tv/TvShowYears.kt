package com.pdm.firebase.feature.domain.model.tv

import java.text.SimpleDateFormat
import java.util.*

object TvShowYears {

    fun getTvShowsYears(): Pair<Float, Float> {
        val actualData = SimpleDateFormat("yyyy", Locale("pt", "BR"))
        val dataInteger = Integer.parseInt(actualData.format(Date()))
        return Pair(1944F, dataInteger.toFloat())
    }
}
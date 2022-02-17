package com.pdm.firebase.feature.domain.model.movie

import java.text.SimpleDateFormat
import java.util.*

object MovieYears {

    fun getMoviesYears(): Pair<Float, Float> {
        val actualData = SimpleDateFormat("yyyy", Locale("pt", "BR"))
        val dataInteger = Integer.parseInt(actualData.format(Date()))
        return Pair(1878F, dataInteger.toFloat())
    }
}
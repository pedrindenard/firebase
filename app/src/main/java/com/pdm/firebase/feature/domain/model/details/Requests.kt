package com.pdm.firebase.feature.domain.model.details

object Requests {

    fun get(quantity: Int): MutableList<Boolean> {
        val mutableList = mutableListOf<Boolean>()
        (1..quantity).forEach { _ ->
            mutableList.add(false)
        }
        return mutableList
    }
}
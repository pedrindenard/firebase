package com.pdm.firebase.arquitecture

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Failure<out T>(val throwable: Throwable) : Resource<T>()

    data class Error<out T>(var code: Int, var message: String) : Resource<T>()

    data class InvalidAuth<out T>(var message: String) : Resource<T>()

}
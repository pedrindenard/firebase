package com.pdm.firebase.feature.domain.local

interface Cache {

    suspend fun insert(key: String, value: String)

    suspend fun get(key: String) : String

    suspend fun delete(key: String)

    suspend fun clear()

}
package com.pdm.firebase.feature.domain.datasource

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

interface TvShowDataSource {

    suspend fun getTvShowPopular(page: Int): Resource<TvShowResponse?>

    suspend fun getTvShowGenders(): Resource<GenderResponse?>

    suspend fun getTvShowByGender(page: Int, id: Int): Resource<TvShowResponse?>

    suspend fun getTvShowTopRated(page: Int): Resource<TvShowResponse?>

    suspend fun getTvShowOnAir(page: Int): Resource<TvShowResponse?>
}
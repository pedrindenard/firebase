package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

interface TvShowRepository {

    suspend fun getTvShowPopular(page: Int, ignoreCache: Boolean): Resource<TvShowResponse?>

    suspend fun getTvShowGenders(ignoreCache: Boolean): Resource<GenderResponse?>

    suspend fun getTvShowByGender(page: Int, id: Int, ignoreCache: Boolean): Resource<TvShowResponse?>

    suspend fun getTvShowTopRated(page: Int, ignoreCache: Boolean): Resource<TvShowResponse?>

    suspend fun getTvShowOnAir(page: Int, ignoreCache: Boolean): Resource<TvShowResponse?>
}
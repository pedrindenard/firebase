package com.pdm.firebase.feature.domain.usecase.search

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.repository.SearchRepository
import retrofit2.HttpException
import java.io.IOException

class GetSearchTvShowsUseCase(private val repository: SearchRepository) {

    suspend operator fun invoke(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<TvShowResponse?> {
        return try {
            repository.getSearchTvShows(
                query = query, page = page, filter = filter
            )
        } catch (e: HttpException) {
            Resource.Error(
                message = e.message(),
                code = e.code()
            )
        } catch (e: IOException) {
            Resource.Failure(
                throwable = e.cause!!
            )
        }
    }
}
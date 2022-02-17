package com.pdm.firebase.feature.domain.usecase.search

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.repository.SearchRepository
import retrofit2.HttpException
import java.io.IOException

class GetSearchCollectionUseCase(private val repository: SearchRepository) {

    suspend operator fun invoke(
        query: String, page: Int
    ): Resource<CollectionResponse?> {
        return try {
            repository.getSearchCollections(
                query = query, page = page
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
package com.pdm.firebase.feature.domain.usecase.tv.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.repository.TvDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetTvRecommendationsUseCase(private val repository: TvDetailsRepository) {

    suspend operator fun invoke(id: Int, page: Int): Resource<TvShowResponse?> {
        return try {
            repository.getTvRecommendations(
                id = id, page = page
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
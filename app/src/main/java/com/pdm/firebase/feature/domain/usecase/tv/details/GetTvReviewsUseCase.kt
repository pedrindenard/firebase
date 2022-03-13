package com.pdm.firebase.feature.domain.usecase.tv.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.repository.TvDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetTvReviewsUseCase(private val repository: TvDetailsRepository) {

    suspend operator fun invoke(id: Int, page: Int): Resource<ReviewResponse?> {
        return try {
            repository.getTvReviews(
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
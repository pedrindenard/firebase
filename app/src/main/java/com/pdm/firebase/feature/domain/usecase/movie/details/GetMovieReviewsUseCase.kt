package com.pdm.firebase.feature.domain.usecase.movie.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.repository.MovieDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetMovieReviewsUseCase(private val repository: MovieDetailsRepository) {

    suspend operator fun invoke(id: Int, page: Int): Resource<ReviewResponse?> {
        return try {
            repository.getMovieReviews(
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
package com.pdm.firebase.feature.domain.usecase.movie.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.movie.MovieCreditsResponse
import com.pdm.firebase.feature.domain.repository.MovieDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetMovieCreditsUseCase(private val repository: MovieDetailsRepository) {

    suspend operator fun invoke(id: Int): Resource<MovieCreditsResponse?> {
        return try {
            repository.getMovieCredits(
                id = id
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
package com.pdm.firebase.feature.domain.usecase.movie.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.repository.MovieDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetMovieSimilarUseCase(private val repository: MovieDetailsRepository) {

    suspend operator fun invoke(id: Int, page: Int): Resource<MovieResponse?> {
        return try {
            repository.getMovieSimilar(
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
package com.pdm.firebase.feature.domain.usecase.movie.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.repository.MovieDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetMovieImagesUseCase(private val repository: MovieDetailsRepository) {

    suspend operator fun invoke(id: Int): Resource<ImageResponse?> {
        return try {
            repository.getMovieImages(
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
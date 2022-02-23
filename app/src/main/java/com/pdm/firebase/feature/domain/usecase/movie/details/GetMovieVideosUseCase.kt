package com.pdm.firebase.feature.domain.usecase.movie.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.repository.MovieDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetMovieVideosUseCase(private val repository: MovieDetailsRepository) {

    suspend operator fun invoke(id: Int): Resource<VideoResponse?> {
        return try {
            repository.getMovieVideos(
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
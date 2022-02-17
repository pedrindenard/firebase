package com.pdm.firebase.feature.domain.usecase.movie

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class GetNowPlayingMovieUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(page: Int): Resource<MovieResponse?> {
        return try {
            repository.getMovieNowPlaying(
                page = page
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
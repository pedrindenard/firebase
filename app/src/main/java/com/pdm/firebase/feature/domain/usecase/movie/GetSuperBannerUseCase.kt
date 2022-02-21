package com.pdm.firebase.feature.domain.usecase.movie

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class GetSuperBannerUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(page: Int, ignoreCache: Boolean? = false): Resource<MovieResponse?> {
        return try {
            repository.getSuperBanner(
                page = page, ignoreCache = ignoreCache!!
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
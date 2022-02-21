package com.pdm.firebase.feature.domain.usecase.movie

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class GetGendersMovieUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(ignoreCache: Boolean? = false): Resource<GenderResponse?> {
        return try {
            repository.getGendersMovie(
                ignoreCache = ignoreCache!!
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
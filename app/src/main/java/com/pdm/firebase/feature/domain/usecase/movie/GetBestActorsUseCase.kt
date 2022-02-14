package com.pdm.firebase.feature.domain.usecase.movie

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class GetBestActorsUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(refresh: Boolean? = false): Resource<ActorsResponse?> {
        return try {
            repository.getBestActors(
                refresh = refresh!!
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
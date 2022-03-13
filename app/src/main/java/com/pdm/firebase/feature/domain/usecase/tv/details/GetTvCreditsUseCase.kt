package com.pdm.firebase.feature.domain.usecase.tv.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.movie.CreditsResponse
import com.pdm.firebase.feature.domain.repository.TvDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetTvCreditsUseCase(private val repository: TvDetailsRepository) {

    suspend operator fun invoke(id: Int): Resource<CreditsResponse?> {
        return try {
            repository.getTvCredits(
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
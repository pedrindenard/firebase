package com.pdm.firebase.feature.domain.usecase.people.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.movie.MovieCreditsResponse
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.repository.PeopleDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetPeopleCreditsUseCase(private val repository: PeopleDetailsRepository) {

    suspend operator fun invoke(id: Int): Resource<PeopleCreditsResponse?> {
        return try {
            repository.getPeopleCredits(
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
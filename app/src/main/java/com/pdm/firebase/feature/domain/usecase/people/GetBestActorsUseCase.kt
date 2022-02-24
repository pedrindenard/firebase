package com.pdm.firebase.feature.domain.usecase.people

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.people.PeopleResponse
import com.pdm.firebase.feature.domain.repository.PeopleRepository
import retrofit2.HttpException
import java.io.IOException

class GetBestActorsUseCase(private val repository: PeopleRepository) {

    suspend operator fun invoke(page: Int, ignoreCache: Boolean? = false): Resource<PeopleResponse?> {
        return try {
            repository.getBestActors(
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
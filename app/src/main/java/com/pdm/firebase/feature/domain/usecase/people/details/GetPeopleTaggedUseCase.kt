package com.pdm.firebase.feature.domain.usecase.people.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.tagged.TaggedResponse
import com.pdm.firebase.feature.domain.repository.PeopleDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetPeopleTaggedUseCase(private val repository: PeopleDetailsRepository) {

    suspend operator fun invoke(id: Int, page: Int): Resource<TaggedResponse?> {
        return try {
            repository.getPeopleTagged(
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
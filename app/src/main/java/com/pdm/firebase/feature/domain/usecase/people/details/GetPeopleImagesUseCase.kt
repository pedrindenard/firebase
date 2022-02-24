package com.pdm.firebase.feature.domain.usecase.people.details

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.repository.PeopleDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class GetPeopleImagesUseCase(private val repository: PeopleDetailsRepository) {

    suspend operator fun invoke(id: Int): Resource<ImageResponse?> {
        return try {
            repository.getPeopleImages(
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
package com.pdm.firebase.feature.domain.usecase.tv

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.repository.TvShowRepository
import retrofit2.HttpException
import java.io.IOException

class GetGendersTvUseCase(private val repository: TvShowRepository) {

    suspend operator fun invoke(ignoreCache: Boolean? = false): Resource<GenderResponse?> {
        return try {
            repository.getGendersTv(
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
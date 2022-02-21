package com.pdm.firebase.feature.domain.usecase.discover

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.repository.DiscoveryRepository
import retrofit2.HttpException
import java.io.IOException

class GetTvShowByQueryUseCase(private val repository: DiscoveryRepository) {

    suspend operator fun invoke(id: Int, sort: String, page: Int): Resource<TvShowResponse?> {
        return try {
            repository.getTvShowByQuery(
                id = id, sort = sort, page = page
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
package com.pdm.firebase.feature.presentation.fragment.splash.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val useCase: MovieUseCase,
    private val cache: CacheImpl
) : BaseViewModel() {

    val startIntroActivity = MutableLiveData<Boolean>()

    fun start() {
        viewModelScope.launch {
            when (val response = useCase.getGendersMovie.invoke()) {
                is Resource.Success -> {
                    useCase.getMovieByGender.invoke(
                        id = response.data!!.genres.first().id
                    )
                }
                else -> {
                    /** Do nothing **/
                }
            }

            useCase.getSuperBanner.invoke()
            useCase.getPopularMovie.invoke()
            useCase.getUpcomingMovie.invoke()
            useCase.getBestActors.invoke()

            startIntroActivity.postValue(
                cache.get(CacheImpl.DEFAULT).isEmpty()
            )
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            cache.delete(CacheImpl.HOME_BANNER)
            cache.delete(CacheImpl.POPULAR_MOVIE)
            cache.delete(CacheImpl.RATED_MOVIE)
            cache.delete(CacheImpl.GENDERS_MOVIE)
            cache.delete(CacheImpl.GENDERS_TV)
            cache.delete(CacheImpl.MOVIE_BY_GENDER)
            cache.delete(CacheImpl.UPCOMING_MOVIE)
            cache.delete(CacheImpl.BEST_ACTORS)
        }
    }
}
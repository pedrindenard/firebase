package com.pdm.firebase.feature.presentation.fragment.splash.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.TvShowUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvShowUseCase: TvShowUseCase,
    private val cache: CacheImpl
) : BaseViewModel() {

    val startIntroActivity = MutableLiveData<Boolean>()

    fun start() {
        viewModelScope.launch {
            when (val response = movieUseCase.getGendersMovie.invoke()) {
                is Resource.Success -> {
                    movieUseCase.getMovieByGender.invoke(
                        id = response.data!!.genres.first().id,
                        page = 1
                    )
                }
                else -> {
                    /** Do nothing **/
                }
            }

            movieUseCase.getSuperBanner.invoke(page = 1)
            movieUseCase.getPopularMovie.invoke(page = 1)
            movieUseCase.getUpcomingMovie.invoke(page = 1)
            movieUseCase.getBestActors.invoke(page = 1)

            tvShowUseCase.getTvShowPopular.invoke(page = 1)
            tvShowUseCase.getTvShowTopRated.invoke(page = 1)

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
            cache.delete(CacheImpl.TOP_RATED_TV)
            cache.delete(CacheImpl.REGIONS)
            cache.delete(CacheImpl.POPULAR_TV)
            cache.delete(CacheImpl.NOW_PLAYING_MOVIE)
        }
    }
}
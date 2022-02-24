package com.pdm.firebase.feature.presentation.fragment.init.viewModel

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

    init { clearCache() }

    fun start() {
        viewModelScope.launch {
            when (val response = movieUseCase.getGendersMovie.invoke(ignoreCache = true)) {
                is Resource.Success -> {
                    movieUseCase.getMovieByGender.invoke(
                        id = response.data!!.genres.first().id,
                        ignoreCache = true,
                        page = 1
                    )
                }
                else -> {
                    /** Do nothing **/
                }
            }

            movieUseCase.getRatedMovie.invoke(page = 1, ignoreCache = true)
            movieUseCase.getNowPlayingMovie.invoke(page = 1, ignoreCache = true)
            movieUseCase.getPopularMovie.invoke(page = 1, ignoreCache = true)
            movieUseCase.getUpcomingMovie.invoke(page = 1, ignoreCache = true)

            when (val response = tvShowUseCase.getGendersTvShow.invoke(ignoreCache = true)) {
                is Resource.Success -> {
                    tvShowUseCase.getTvShowByGender.invoke(
                        id = response.data!!.genres.first().id,
                        ignoreCache = true,
                        page = 1
                    )
                }
                else -> {
                    /** Do nothing **/
                }
            }

            tvShowUseCase.getTvShowPopular.invoke(page = 1, ignoreCache = true)
            tvShowUseCase.getTvShowTopRated.invoke(page = 1, ignoreCache = true)
            tvShowUseCase.getTvShowOnAir.invoke(page = 1, ignoreCache = true)

            startIntroActivity.postValue(
                cache.get(CacheImpl.DEFAULT).isEmpty()
            )
        }
    }

    private fun clearCache() {
        viewModelScope.launch {
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
            cache.delete(CacheImpl.TV_BY_GENDER)
            cache.delete(CacheImpl.ON_AIR_TV)
        }
    }
}
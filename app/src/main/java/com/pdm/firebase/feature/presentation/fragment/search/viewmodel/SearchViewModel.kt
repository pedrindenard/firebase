package com.pdm.firebase.feature.presentation.fragment.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.usecase.SearchUseCase
import com.pdm.firebase.feature.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel(private val useCase: SearchUseCase) : BaseViewModel() {

    private val _getSearchCollections: MutableLiveData<CollectionResponse?> = MutableLiveData()
    val getSearchCollections = _getSearchCollections as LiveData<CollectionResponse?>
    private var searchCollectionListOld: CollectionResponse? = null

    private val _getSearchMovies: MutableLiveData<MovieResponse?> = MutableLiveData()
    val getSearchMovies = _getSearchMovies as LiveData<MovieResponse?>
    private var searchMoviesListOld: MovieResponse? = null

    private val _getSearchTvShows: MutableLiveData<TvShowResponse?> = MutableLiveData()
    val getSearchTvShows = _getSearchTvShows as LiveData<TvShowResponse?>
    private var searchTvShowsListOld: TvShowResponse? = null

    private val _getSearchMulti: MutableLiveData<SearchResponse?> = MutableLiveData()
    val getSearchMulti = _getSearchMulti as LiveData<SearchResponse?>
    private var searchMultiListOld: SearchResponse? = null

    private val _getSearchActors: MutableLiveData<ActorsResponse?> = MutableLiveData()
    val getSearchActors = _getSearchActors as LiveData<ActorsResponse?>
    private var searchActorsListOld: ActorsResponse? = null

    private val _getRegions: MutableLiveData<RegionResponse> = MutableLiveData()
    val getRegions = _getRegions as LiveData<RegionResponse>

    private var currentSearchPage = 1

    fun searchCollection(query: String) {
        viewModelScope.launch {
            when (val response = useCase.getSearchCollections.invoke(query, currentSearchPage)) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchCollectionListOld?.apply {
                            results.addAll(it.results)
                            currentPage = currentSearchPage
                        } ?: run { searchCollectionListOld = it }
                        _getSearchCollections.postValue(searchCollectionListOld ?: it)
                    }; currentSearchPage++
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun searchMovie(query: String, filter: FilterCreated?) {
        viewModelScope.launch {
            when (val response = useCase.getSearchMovies.invoke(query, currentSearchPage, filter)) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchMoviesListOld?.apply {
                            results.addAll(it.results)
                            currentPage = currentSearchPage
                        } ?: run { searchMoviesListOld = it }
                        _getSearchMovies.postValue(searchMoviesListOld ?: it)
                    }; currentSearchPage++
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun searchTvShow(query: String, filter: FilterCreated?) {
        viewModelScope.launch {
            when (val response =
                useCase.getSearchTvShows.invoke(query, currentSearchPage, filter)) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchTvShowsListOld?.apply {
                            results.addAll(it.results)
                            currentPage = currentSearchPage
                        } ?: run { searchTvShowsListOld = it }
                        _getSearchTvShows.postValue(searchTvShowsListOld ?: it)
                    }; currentSearchPage++
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun searchMulti(query: String, filter: FilterCreated?) {
        viewModelScope.launch {
            when (val response = useCase.getSearchMulti.invoke(query, currentSearchPage, filter)) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchMultiListOld?.apply {
                            results.addAll(it.results)
                            currentPage = currentSearchPage
                        } ?: run { searchMultiListOld = it }
                        _getSearchMulti.postValue(searchMultiListOld ?: it)
                    }; currentSearchPage++
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun searchPeople(query: String, filter: FilterCreated?) {
        viewModelScope.launch {
            when (val response = useCase.getSearchPeople.invoke(query, currentSearchPage, filter)) {
                is Resource.Success -> {
                    response.data!!.let {
                        searchActorsListOld?.apply {
                            results.addAll(it.results)
                            currentPage = currentSearchPage
                        } ?: run { searchActorsListOld = it }
                        _getSearchActors.postValue(searchActorsListOld ?: it)
                    }; currentSearchPage++
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun getRegions() {
        viewModelScope.launch {
            when (val response = useCase.getRegions.invoke()) {
                is Resource.Success -> {
                    _getRegions.postValue(response.data!!)
                }
                is Resource.Error -> {
                    errorResponse.postValue(response.message)
                }
                is Resource.Failure -> {
                    failureResponse.postValue(response.throwable)
                }
                is Resource.InvalidAuth -> {
                    invalidAuth.postValue(response.message)
                }
            }
        }
    }

    fun clearOldLists() {
        _getSearchCollections.value = null
        _getSearchMovies.value = null
        _getSearchTvShows.value = null
        _getSearchMulti.value = null
        _getSearchActors.value = null

        searchCollectionListOld = null
        searchTvShowsListOld = null
        searchMoviesListOld = null
        searchActorsListOld = null
        searchMultiListOld = null

        currentSearchPage = 1
    }
}
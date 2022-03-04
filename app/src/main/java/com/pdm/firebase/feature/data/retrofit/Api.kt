package com.pdm.firebase.feature.data.retrofit

import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.model.company.CompanyResponse
import com.pdm.firebase.feature.domain.model.credit.movie.MovieCreditsResponse
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.movie.details.MovieDetailsResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.people.PeopleResponse
import com.pdm.firebase.feature.domain.model.people.details.PeopleDetailsResponse
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.tagged.TaggedResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    /** People **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/popular")
    suspend fun getBestActors(
        @Query(value = "page") page: Int? = 1
    ): Response<PeopleResponse>

    /** Movies **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/popular")
    suspend fun getPopularMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/top_rated")
    suspend fun getRatedMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "genre/movie/list")
    suspend fun getGendersMovie(): Response<GenderResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/movie?sort_by=popularity.desc")
    suspend fun getMovieByGender(
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/upcoming")
    suspend fun getUpcomingMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/movie")
    suspend fun getMovieByQuery(
        @Query(value = "sort_by") sort: String,
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int
    ): Response<MovieResponse>

    /** Movie details **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path(value = "movie_id") id: Int
    ): Response<MovieDetailsResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path(value = "movie_id") id: Int
    ): Response<MovieCreditsResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path(value = "movie_id") id: Int,
        @Query(value = "page") page: Int
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Path(value = "movie_id") id: Int,
        @Query(value = "page") page: Int
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(
        @Path(value = "movie_id") id: Int
    ): Response<ProviderResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path(value = "movie_id") id: Int,
        @Query(value = "page") page: Int
    ): Response<ReviewResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path(value = "movie_id") id: Int
    ): Response<VideoResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path(value = "movie_id") id: Int
    ): Response<ImageResponse>

    /** People details **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/{person_id}")
    suspend fun getPeopleDetails(
        @Path(value = "person_id") id: Int
    ): Response<PeopleDetailsResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/{person_id}/combined_credits")
    suspend fun getPeopleCredits(
        @Path(value = "person_id") id: Int
    ): Response<PeopleCreditsResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/{person_id}/tagged_images")
    suspend fun getPeopleTagged(
        @Path(value = "person_id") id: Int,
        @Query(value = "page") page: Int? = 1
    ): Response<TaggedResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/{person_id}/images")
    suspend fun getPeopleImages(
        @Path(value = "person_id") id: Int
    ): Response<ImageResponse>

    /** Company details **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "company/{company_id}")
    suspend fun getCompanyDetails(
        @Path(value = "company_id") id: Int
    ): Response<CompanyResponse>

    /** Search **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/collection")
    suspend fun searchCollection(
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<CollectionResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/movie")
    suspend fun searchMovie(
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "year") year: Int? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/tv")
    suspend fun searchTvShow(
        @Query(value = "first_air_date_year") year: Int? = null,
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/multi")
    suspend fun searchMulti(
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<SearchResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/person")
    suspend fun searchActor(
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<PeopleResponse>

    /** Configs **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "watch/providers/regions")
    suspend fun getRegions() : Response<RegionResponse>

    /** Tv shows **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/tv")
    suspend fun getTvShowByQuery(
        @Query(value = "sort_by") sort: String,
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/on_the_air")
    suspend fun getTvShowOnAir(
        @Query(value = "page") page: Int
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/popular")
    suspend fun getTvShowPopular(
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/top_rated")
    suspend fun getTvShowTopRated(
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/tv?sort_by=popularity.desc")
    suspend fun getTvShowByGender(
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "genre/tv/list")
    suspend fun getGendersTv(): Response<GenderResponse>

    /** Tv Details **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}")
    suspend fun getTvDetails(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/credits")
    suspend fun getTvCredits(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/similar")
    suspend fun getTvSimilar(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/images")
    suspend fun getTvImages(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/reviews")
    suspend fun getTvReviews(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/watch/providers")
    suspend fun getTvProviders(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/videos")
    suspend fun getTvVideos(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/recommendations")
    suspend fun getTvRecommendations(
        @Path(value = "tv_id") id: Int
    ): Response<*>

    /** Tv Seasons **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}")
    suspend fun getTvSeasonDetails(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/videos")
    suspend fun getTvSeasonVideos(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/images")
    suspend fun getTvSeasonImages(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/credits")
    suspend fun getTvSeasonCredits(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    /** Tv Episodes **/
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/{episode}")
    suspend fun getTvEpisodeDetails(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/{episode}/videos")
    suspend fun getTvEpisodeVideos(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/{episode}/images")
    suspend fun getTvEpisodeImages(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/{tv_id}/season/{season_number}/{episode}/credits")
    suspend fun getTvEpisodeCredits(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") season: Int
    ): Response<*>
}
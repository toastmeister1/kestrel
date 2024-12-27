package com.toastmeister1.kestrel.core.data.api

import com.toastmeister1.kestrel.core.data.model.MovieDetailResponse
import com.toastmeister1.kestrel.core.data.model.MovieSearchResponse
import com.toastmeister1.kestrel.core.data.util.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/movie_id")
    suspend fun fetchDetail(
        @Path("movie_id") movieId: Int,
    ): ApiResult<MovieDetailResponse>

    @GET("search/collection")
    suspend fun searchQuery(
        @Query("query") query: String,
        @Query("page") page: Int
    ): ApiResult<MovieSearchResponse>

    @GET("movie/popular")
    suspend fun fetchPopular(
        @Query("page") page: Int
    ): ApiResult<MovieSearchResponse>
}

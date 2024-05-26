package com.toastmeister1.kestrel.core.data.api

import com.toastmeister1.kestrel.core.data.model.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApi {

    @GET("movie/movie_id\n")
    suspend fun fetchDetail(
        @Path("movie_id") movieId: Int,
    ): MovieDetailResponse
}

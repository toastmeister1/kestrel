package com.toastmeister1.kestrel.core.data.repository

import toastmeister.one.model.movie.MovieDetail
import toastmeister.one.model.movie.MovieSearchResult

interface MovieRepository {
    suspend fun fetchDetail(movieId: Int): Result<List<MovieDetail>>

    suspend fun searchQuery(query: String, page: Int): Result<MovieSearchResult>

    suspend fun fetchPopular(page: Int): Result<MovieSearchResult>
}

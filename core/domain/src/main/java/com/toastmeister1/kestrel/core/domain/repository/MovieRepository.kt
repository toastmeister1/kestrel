package com.toastmeister1.kestrel.core.domain.repository

import com.toastmeister1.kestrel.core.domain.model.MovieDetail
import com.toastmeister1.kestrel.core.domain.model.MovieSearchResult

interface MovieRepository {
    suspend fun fetchDetail(movieId: Int): Result<MovieDetail>
    suspend fun searchQuery(query: String, page: Int): Result<MovieSearchResult>
}

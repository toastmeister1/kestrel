package com.toastmeister1.kestrel.core.data.repository

import com.toastmeister1.kestrel.core.data.api.TMDBApi
import com.toastmeister1.kestrel.core.domain.model.MovieDetail
import com.toastmeister1.kestrel.core.domain.model.MovieSearchResult
import com.toastmeister1.kestrel.core.domain.repository.MovieRepository
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val movieService: TMDBApi
) : MovieRepository {

    override suspend fun fetchDetail(movieId: Int): Result<MovieDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun searchQuery(query: String, page: Int): Result<MovieSearchResult> {
        TODO("Not yet implemented")
    }
}
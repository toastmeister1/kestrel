package com.toastmeister1.kestrel.core.data.repository

import com.toastmeister1.kestrel.core.data.api.TMDBApi
import com.toastmeister1.kestrel.core.data.util.ApiResult
import toastmeister.one.model.movie.Movie
import toastmeister.one.model.movie.MovieDetail
import toastmeister.one.model.movie.MovieSearchResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MovieRepositoryImpl @Inject constructor(
    private val movieService: TMDBApi
) : MovieRepository {

    override suspend fun fetchDetail(movieId: Int): Result<List<MovieDetail>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchQuery(query: String, page: Int): Result<MovieSearchResult> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPopular(page: Int): Result<MovieSearchResult> {
        return when (val result = movieService.fetchPopular(page)) {
            is ApiResult.Success -> {
                val model = MovieSearchResult(
                    page = result.data.page,
                    totalPages = result.data.totalPages,
                    totalResults = result.data.totalResults,
                    results = result.data.results.map {
                        Movie(
                            id = it.id,
                            originalTitle = it.originalTitle,
                            backdropPath = it.backdropPath,
                            posterPath = it.posterPath,
                            overview = it.overview
                        )
                    }
                )

                Result.success(model)
            }

            is ApiResult.Error -> {
                Result.failure(RuntimeException(result.message))
            }

            is ApiResult.Exception -> {
                Result.failure(result.throwable)
            }
        }
    }
}
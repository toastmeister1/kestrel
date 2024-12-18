package com.toastmeister1.kestrel.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<SearchResult>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
) {

    @Serializable
    data class SearchResult(
        @SerialName("id")
        val id: Int,
        @SerialName("original_name")
        val originalName: String,
        @SerialName("backdrop_path")
        val backdropPath: String,
        @SerialName("poster_path")
        val posterPath: String,
        @SerialName("overview")
        val overview: String,
    )
}
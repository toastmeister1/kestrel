package com.toastmeister1.kestrel.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("overview")
    val overview: String
)
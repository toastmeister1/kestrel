package com.toastmeister1.kestrel.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String
)
package toastmeister.one.model.movie

data class Movie(
    val id: Int,
    val originalTitle: String,
    val backdropPath: String,
    val posterPath: String,
    val overview: String,
)
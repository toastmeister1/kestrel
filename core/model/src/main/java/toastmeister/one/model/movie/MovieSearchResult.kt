package toastmeister.one.model.movie

data class MovieSearchResult(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
package com.farasatnovruzov.movieappcompose.model

data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
    val response: String,
    val errorMessage: String,
    val error: String,
    val message: String,
    val items: List<Movie>,
    val totalResults: String,
    val images: List<String>,

)

fun getMovies(): List<Movie>{
    return listOf(
        Movie(
            id = "tt0499549",
            title = "The Dark Knight",
            year = "2008",
            genre = "Action, Crime, Drama",
            director = "Christopher Nolan",
            actors = "Christian Bale, Heath Ledger, Aaron Eckhart",
            plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg",
            imdbRating = "9.0",
            imdbVotes = "2,123,456",
            type = "movie",
            response = "True",
            errorMessage = "",
            error = "",
            message = "",
            items = emptyList(),
            totalResults = "",
            images = listOf("https://upload.wikimedia.org/wikipedia/en/8/83/Dark_knight_rises_poster.jpg",
                "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg")
        ),
        Movie(
            id = "tt0071562",
            title = "The Shawshank Redemption",
            year = "1994",
            genre = "Drama",
            director = "Frank Darabont",
            actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
            plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            poster = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
            imdbRating = "9.3",
            imdbVotes = "2,123,456",
            type = "movie",
            response = "True",
            errorMessage = "",
            error = "",
            message = "",
            items = emptyList(),
            totalResults = "",
            images = listOf("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
                "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMT")
        )
    )
}

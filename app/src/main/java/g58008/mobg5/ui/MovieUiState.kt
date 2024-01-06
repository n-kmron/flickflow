package g58008.mobg5.ui

import g58008.mobg5.network.Image
import g58008.mobg5.network.ReleaseDate
import g58008.mobg5.network.TitleText

/**
 * Representing the UI state of the application.
 *
 * @property isMovie A boolean indicating if a movie is present.
 * @property moviePosition The position of the movie in the boxoffice.
 */
data class MovieUiState(
    val isMoviePresent : Boolean = false,
    val movieId: String = "",
    val movieImageUrl : Image = Image(""),
    val movieTitle : TitleText = TitleText(""),
    val movieReleaseDate : ReleaseDate = ReleaseDate(1, 1, 2000),
    val moviePosition : Int = 0,
)

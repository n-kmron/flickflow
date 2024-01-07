package g58008.mobg5.ui

import g58008.mobg5.network.Image
import g58008.mobg5.network.ReleaseDate
import g58008.mobg5.network.TitleText

/**
 * Representing the UI state of the application.
 *
 * @property currentEmail The current email entered by the user.
 * @property currentPassword The current password entered by the user.
 * @property isEmailFormatValid A boolean indicating whether the email format is valid.
 * @property authorized A boolean indicating whether the user is authenticated.
 */
data class AppUiState(
    //login state
    val currentEmail: String = "",
    val currentPassword: String = "",
    val isEmailFormatValid : Boolean = true,
    val authorized : Boolean = false,

    //movie state
    val isMoviePresent : Boolean = false,
    val movieId: String = "",
    val movieImageUrl : Image = Image(""),
    val movieTitle : TitleText = TitleText(""),
    val movieReleaseDate : ReleaseDate = ReleaseDate(1, 1, 2000),
    val moviePosition : Int = 0,
)

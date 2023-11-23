package g58008.mobg5.ui

/**
 * Representing the UI state of the application.
 *
 * @property currentEmail The current email entered by the user.
 * @property currentPassword The current password entered by the user.
 * @property isEmailFormatValid A boolean indicating whether the email format is valid.
 * @property isPasswordFormatValid A boolean indicating whether the password format is valid.
 * @property authorized A boolean indicating whether the user is authenticated.
 */
data class AppUiState(
    val currentEmail: String = "",
    val currentPassword: String = "",
    val isEmailFormatValid : Boolean = true,
    val isPasswordFormatValid: Boolean = true,
    val authorized : Boolean = false
)

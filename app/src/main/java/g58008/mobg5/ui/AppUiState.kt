package g58008.mobg5.ui

/**
 * Representing the UI state of the application.
 *
 * @property currentEmail The current email entered by the user.
 * @property currentPassword The current password entered by the user.
 * @property isEmailFormatValid A boolean indicating whether the email format is valid.
 * @property isPasswordFormatValid A boolean indicating whether the password format is valid.
 * @property isValidLogin A boolean indicating whether the login is valid.
 */
data class AppUiState(
    val currentEmail: String = "",
    val currentPassword: String = "",
    val isEmailFormatValid : Boolean = true,
    val isPasswordFormatValid: Boolean = true,
    val isValidLogin : Boolean = false
)

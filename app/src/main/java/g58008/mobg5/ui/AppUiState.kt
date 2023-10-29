package g58008.mobg5.ui

/**
 * Represents the current UI state of the application.
 *
 * @property currentEmail The current email entered by the user.
 * @property currentPassword The current password entered by the user.
 * @property isEmailWrong Indicates whether the email is in an incorrect format.
 * @property isPasswordWrong Indicates whether the password does not meet the criteria.
 * @property isValidLogin Indicates whether the entered email and password result in a valid login.
 */
data class AppUiState(
    val currentEmail: String = "",
    val currentPassword: String = "",
    val isEmailWrong : Boolean = false,
    val isPasswordWrong : Boolean = false,
    val isValidLogin : Boolean = false
)

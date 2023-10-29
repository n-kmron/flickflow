package g58008.mobg5.ui

data class AppUiState(
    val currentEmail: String = "",
    val currentPassword: String = "",
    val isEmailWrong : Boolean = false,
    val isPasswordWrong : Boolean = false,
    val isValidLogin : Boolean = false
)

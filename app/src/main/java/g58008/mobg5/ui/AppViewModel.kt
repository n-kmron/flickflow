package g58008.mobg5.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel class responsible for managing the app's UI state and user data.
 * This class handles user input, data validation, and updates to the UI state.
 *
 * @property uiState Represents the current state of the app's user interface.
 * @property userEmail The email entered by the user.
 * @property userPassword The password entered by the user.
 */
class AppViewModel : ViewModel() {

    companion object {
        const val EMAIL_DEBUG: String = "abc@abc.be"
    }
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun updateUserEmail(email : String) {
        userEmail = email
    }

    fun updateUserPassword(password: String) {
        userPassword = password
    }


    /**
     * Update the app's UI state based on the provided email and password.
     * Check if the data of fields is correct
     *
     * @param email The email entered by the user.
     * @param password The password entered by the user.
     */
    private fun updateGameState(email : String, password: String) {
        // TODO change this condition to match to a real record in db
        if (email == EMAIL_DEBUG){
            //login matching to a record
            _uiState.update { currentState ->
                currentState.copy(
                    isValidLogin = true,
                    isPasswordWrong = false,
                    isEmailWrong = false,
                )
            }
        } else {
            //login no matching to a record
            _uiState.update { currentState ->
                currentState.copy(
                    isValidLogin = false,
                    isPasswordWrong = false,
                    isEmailWrong = false,
                    currentEmail = email,
                    currentPassword = password,
                )
            }
        }
    }


    /**
     * Check the user's provided data and update the app's UI state accordingly.
     * - `isEmailValid` checks if the user's entered email is in a valid format.
     * - `isPasswordValid` checks if the user's entered password meets the criteria (at least 8 characters with an uppercase letter and a digit).
     *
     * If the email is invalid, the UI state indicates an email error. If the password is also invalid, the UI state indicates both email and password errors.
     * If both email and password are valid, the UI state reflects a valid login.
     * After the check, the user data is reset.
     */
    fun checkUserData() {
        val isEmailValid = isEmailValid(userEmail)
        val isPasswordValid = isPasswordValid(userPassword)

        _uiState.value = _uiState.value.copy(
            isEmailWrong = !isEmailValid,
            isPasswordWrong = !isPasswordValid
        )
        if (isEmailValid && isPasswordValid) {
            updateGameState(userEmail, userPassword)
        }
        resetUserData()
    }

    /**
     * Reset the user data by clearing the user's email and password fields.
     */
    private fun resetUserData() {
        updateUserEmail("")
        updateUserPassword("")
    }

    /**
     * Check if a string is as an email format
     */
    private fun isEmailValid(email: String): Boolean {
        val regex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(regex.toRegex())
    }


    /**
     * Check if a password is at least 8 characters and contain an uppercase and a number
     */
    private fun isPasswordValid(password: String): Boolean {
        val upperCaseRegex = ".*[A-Z].*"
        val digitRegex = ".*\\d.*"

        return password.matches(upperCaseRegex.toRegex()) &&
                password.matches(digitRegex.toRegex()) &&
                password.length >= 8
    }

}

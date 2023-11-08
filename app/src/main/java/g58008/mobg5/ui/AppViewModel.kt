package g58008.mobg5.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG: String = "VIEW MODEL"

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
     * Update the game state based on the user data provided by searching a record corresponding to the data.
     *
     * @param email The valid email entered by the user.
     * @param password The valid password entered by the user.
     */
    private fun updateGameState(email : String, password: String) {
        // TODO change this condition to match to a real record in db
        if (email == EMAIL_DEBUG){
            Log.d(TAG, "Matching record found")
            _uiState.value = _uiState.value.copy(
                isValidLogin = true,
            )
        } else {
            Log.d(TAG, "Matching record not found")
            _uiState.value = _uiState.value.copy(
                isValidLogin = true,
                currentEmail = email,
                currentPassword = password,
            )
        }
        Log.d(TAG, "Game state updated")
    }


    /**
     * Check if the user data provided is correct and update game state
     */
    fun checkUserData() {
        val isEmailValid = isEmailValid(userEmail)
        val isPasswordValid = isPasswordValid(userPassword)

        _uiState.value = _uiState.value.copy(
            isEmailFormatValid = isEmailValid,
            isPasswordFormatValid = isPasswordValid,
        )
        if (isEmailValid && isPasswordValid) {
            updateGameState(userEmail, userPassword)
        }
        Log.d(TAG, "User data checked")
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
     * Check if a password is valid
     * A password is valid if it contains at least 8 characters with an uppercase letter and a digit
     */
    private fun isPasswordValid(password: String): Boolean {
        val upperCaseRegex = ".*[A-Z].*"
        val digitRegex = ".*\\d.*"

        return password.matches(upperCaseRegex.toRegex()) &&
                password.matches(digitRegex.toRegex()) &&
                password.length >= 8
    }

}

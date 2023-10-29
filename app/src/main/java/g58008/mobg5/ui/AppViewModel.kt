package g58008.mobg5.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private lateinit var currentEmail: String
    private lateinit var currentPassword: String

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
     * Check if the data of fields is correct
     */
    private fun updateGameState(email : String, password: String) {
        //change this condition
        if (email == "abc@abc.be"){
            //right login data so navigate
            _uiState.update { currentState ->
                currentState.copy(
                    isValidLogin = true,
                    isPasswordWrong = false,
                    isEmailWrong = false,
                )
            }
        } else {
            //wrong login data, so restart
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
     * Check data provided by user to update the app's state
     */
    fun checkUserData() {
        if (isEmailValid(userEmail) && isPasswordValid(userPassword)) {
            updateGameState(userEmail, userPassword)
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isEmailWrong = true,
                    isPasswordWrong = true
                )
            }
        }
        // Reset user data
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
    fun isPasswordValid(password: String): Boolean {
        val upperCaseRegex = ".*[A-Z].*"
        val digitRegex = ".*\\d.*"

        return password.matches(upperCaseRegex.toRegex()) &&
                password.matches(digitRegex.toRegex()) &&
                password.length >= 8
    }

}

package g58008.mobg5.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g58008.mobg5.network.AuthApi
import g58008.mobg5.network.AuthBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AuthUiState {
    data class Success(val response: String) : AuthUiState
    object Error : AuthUiState
    object Loading : AuthUiState
    object Empty : AuthUiState
}


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

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    var authUiState: AuthUiState by mutableStateOf(AuthUiState.Empty)
        private set

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun updateUserEmail(email: String) {
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
    private fun updateGameState(email: String, password: String) {
        viewModelScope.launch {
            authenticate(email, password)

            when (authUiState) {
                is AuthUiState.Success -> {
                    Log.d(TAG, "Matching record found")
                    _uiState.value = _uiState.value.copy(
                        authorized = true,
                    )
                }

                is AuthUiState.Error -> {
                    Log.d(TAG, "Matching record not found")
                    _uiState.value = _uiState.value.copy(
                        authorized = false,
                        currentEmail = email,
                        currentPassword = password,
                    )
                }

                else -> {
                    Log.d(TAG, "Actually loading")
                }
            }
        }
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
        val regex = "^[A-Za-z0-9](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(regex.toRegex())
    }


    /**
     * Check if a password is valid
     * A password is valid if it contains at least 6 characters
     */
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    /**
     * Authenticate the user by searching a record corresponding to the data in a server with a retrofit service
     */
    private suspend fun authenticate(email: String, password: String) {
        val body: AuthBody = generateRequestBody(email, password)
        authUiState = AuthUiState.Loading

        authUiState = try {
            val response = AuthApi.retrofitService.findRecord(body)
            if (response.isSuccessful) {
                Log.d(TAG, response.code().toString() + " " + response.body().toString())
                AuthUiState.Success(response.body().toString())
            } else {
                Log.d(TAG, "Error status : " + response.code().toString())
                AuthUiState.Error
            }
        } catch (e: IOException) {
            Log.d(TAG, "Exception : ${e.message}")
            AuthUiState.Error
        }
    }

    /**
     * Generate a request body from the user's email and password as json with serialization
     */
    private fun generateRequestBody(email: String, password: String): AuthBody {
        return AuthBody(email, password)
    }

}

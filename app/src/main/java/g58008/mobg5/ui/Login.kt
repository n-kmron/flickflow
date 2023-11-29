package g58008.mobg5.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import g58008.mobg5.R
import g58008.mobg5.ui.view_model.LoginViewModel
import g58008.mobg5.ui.view_model.AuthUiState

private const val TAG: String = "LOGIN"

@Composable
fun LoginScreen(
    navigate: () -> Unit,
    padding: PaddingValues = PaddingValues(16.dp)
) {
    val loginViewModel: LoginViewModel = viewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            LoginFields(
                navigate = navigate,
                loginViewModel = loginViewModel,
                authUiState = loginViewModel.authUiState
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoginFooter()
        }
    }
}


@Composable
fun LoginFields(
    loginViewModel: LoginViewModel,
    authUiState: AuthUiState,
    navigate: () -> Unit,
) {
    val appUiState by loginViewModel.appUiState.collectAsState()

    LaunchedEffect(key1 = appUiState, block = {
        tryNavigateToNextScreen(navigate, loginViewModel)
    })

    when (authUiState) {
        is AuthUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is AuthUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        var showPassword by remember { mutableStateOf(false) }

        MyCustomField(
            placeholder = stringResource(id = R.string.askEmail),
            onUserValueChanged = { loginViewModel.updateUserEmail(it) },
            userValue = loginViewModel.userEmail,
            isValueWrong = Pair(
                !appUiState.authorized && appUiState.currentEmail.isNotEmpty(),
                stringResource(id = R.string.wrong_credits)
            ),
            isFormatWrong = Pair(
                !appUiState.isEmailFormatValid,
                stringResource(id = R.string.wrong_email_format)
            ),
            onKeyboardDone = { loginViewModel.checkUserData() },
            leadingIcon = Icons.Default.Email,
            showPassword = true
        ) { }
        MyCustomField(
            placeholder = stringResource(id = R.string.askPassword),
            onUserValueChanged = { loginViewModel.updateUserPassword(it) },
            userValue = loginViewModel.userPassword,
            isValueWrong = Pair(
                !appUiState.authorized && appUiState.currentPassword.isNotEmpty(),
                stringResource(id = R.string.wrong_credits)
            ),
            isFormatWrong = Pair(
                false,
                stringResource(id = R.string.default_exception)
            ),
            onKeyboardDone = { loginViewModel.checkUserData() },
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            showPassword = showPassword
        ) { showPassword = !showPassword }
        LoginButton(
            checkUserData = { loginViewModel.checkUserData() },
        )
    }
}

/**
 * A customizable input field
 *
 * @param placeholder The text to display as a placeholder in the input field.
 * @param userValue The current value entered by the user.
 * @param onUserValueChanged A callback function to handle changes to the user's input value.
 * @param isValueWrong Indicates whether the input value is considered wrong.
 * @param isFormatWrong Indicates whether the input value format is considered wrong.
 * @param onKeyboardDone A callback function triggered when the user presses the "Done" button on the keyboard.
 * @param leadingIcon A number which represent an icon
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCustomField(
    placeholder: String,
    onUserValueChanged: (String) -> Unit,
    isValueWrong: Pair<Boolean, String>,
    isFormatWrong: Pair<Boolean, String>,
    onKeyboardDone: () -> Unit,
    userValue: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    showPassword: Boolean,
    onTogglePasswordVisibility: () -> Unit
) {
    OutlinedTextField(
        value = userValue,
        onValueChange = onUserValueChanged,
        singleLine = true,
        shape = shapes.medium,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            if (isFormatWrong.first) {
                Text(isFormatWrong.second)
            } else if (isValueWrong.first) {
                Text(isValueWrong.second)
            } else {
                Text(placeholder)
            }
        },
        isError = isValueWrong.first || isFormatWrong.first,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (isPassword) {
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { onTogglePasswordVisibility() }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (showPassword) R.drawable.password_show else R.drawable.password_hide
                            ),
                            contentDescription = stringResource(id = R.string.icon_toggle_password)
                        )
                    }
                }
            }
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        )
    )
}

@Composable
fun LoginFooter() {
    val backgroundColor = MaterialTheme.colorScheme.background

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.author),
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = stringResource(id = R.string.copyrights),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
fun LoginButton(
    checkUserData: () -> Unit,
) {
    Button(
        onClick = { checkUserData() },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.login), fontSize = 16.sp)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.verify_connection), modifier = Modifier.padding(16.dp))
    }
}


/**
 * Try to connect to the server
 * @param navigate A callback function to navigate to the next screen
 * if the connection is successful, navigate to the next screen
 * else do nothing
 */
fun tryNavigateToNextScreen(
    navigate: () -> Unit,
    loginViewModel: LoginViewModel,
) {
    if (loginViewModel.authUiState is AuthUiState.Success) {
        Log.d(TAG, "Authentication success")
        navigate()
    } else {
        Log.d(TAG, "Authentication failed")
    }
}
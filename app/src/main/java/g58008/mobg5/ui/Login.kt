package g58008.mobg5.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import g58008.mobg5.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    Scaffold(
        topBar = {
            LoginHeader()
        }
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                LoginFields()
                Spacer(modifier = Modifier.height(16.dp))
                LoginFooter()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .align(Alignment.CenterVertically),
                    painter = painterResource(R.drawable.esi_logo),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                )
            }
        },
        modifier = modifier
    )
}


@Composable
fun LoginFields(
    appViewModel: AppViewModel = viewModel(),
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        DemoField(
            placeholder = stringResource(id = R.string.askEmail),
            onUserValueChanged = { appViewModel.updateUserEmail(it) },
            userValue = appViewModel.userEmail,
            isValueWrong = Pair(
                appUiState.isEmailWrong,
                stringResource(id = R.string.wrong_email)),
            isFormatWrong = Pair(
                appUiState.isEmailFormatWrong,
                stringResource(id = R.string.wrong_email_format)),
            onKeyboardDone = { appViewModel.checkUserData() },
            visualTransformation = VisualTransformation.None,
            leadingIcon = R.drawable.email,
            )
        DemoField(
            placeholder = stringResource(id = R.string.askPassword),
            onUserValueChanged = { appViewModel.updateUserPassword(it) },
            userValue = appViewModel.userPassword,
            isValueWrong = Pair(
                appUiState.isPasswordWrong,
                stringResource(id = R.string.wrong_password)),
            isFormatWrong = Pair(
                appUiState.isPasswordFormatWrong,
                stringResource(id = R.string.wrong_password_format)),
            onKeyboardDone = { appViewModel.checkUserData() },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = R.drawable.password,
            )
        LoginButton(onClick = { appViewModel.checkUserData() })
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
 * @param visualTransformation The visual transformation to apply to the input value (e.g., for password masking).
 * @param onKeyboardDone A callback function triggered when the user presses the "Done" button on the keyboard.
 * @param leadingIcon A number which represent an icon
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoField(
    placeholder: String,
    onUserValueChanged: (String) -> Unit,
    isValueWrong: Pair<Boolean, String>,
    isFormatWrong: Pair<Boolean, String>,
    onKeyboardDone: () -> Unit,
    userValue: String,
    visualTransformation: VisualTransformation,
    @DrawableRes leadingIcon : Int,
) {
    OutlinedTextField(
        value = userValue,
        onValueChange = onUserValueChanged,
        singleLine = true,
        shape = shapes.medium,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            if(isFormatWrong.first) {
                Text(isFormatWrong.second)
            }
            else if (isValueWrong.first) {
                Text(isValueWrong.second)
            }
            else {
                Text(placeholder)
            }
        },
        isError = isValueWrong.first || isFormatWrong.first,
        leadingIcon = {
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = stringResource(id = R.string.icon_textfield)
                )
            }
        },
        visualTransformation = visualTransformation,
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
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.login), fontSize = 16.sp)
    }
}


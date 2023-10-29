package g58008.mobg5.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import g58008.mobg5.R

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LoginHeader()
            Spacer(modifier = Modifier.height(16.dp))
            LoginFields()
        }
    }
}

@Composable
fun LoginHeader() {
    Text(
        text = stringResource(id = R.string.app_name),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun LoginFields(
    appViewModel: AppViewModel = viewModel(),
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column {
            DemoField(
                placeholder = stringResource(id = R.string.askEmail),
                onUserValueChanged = { appViewModel.updateUserEmail(it) },
                userValue = appViewModel.userEmail,
                isValueWrong = appUiState.isEmailWrong,
                onKeyboardDone = { appViewModel.checkUserData() },
                visualTransformation = VisualTransformation.None
            )
            DemoField(
                placeholder = stringResource(id = R.string.askPassword),
                onUserValueChanged = { appViewModel.updateUserPassword(it) },
                userValue = appViewModel.userPassword,
                isValueWrong = appUiState.isPasswordWrong,
                onKeyboardDone = { appViewModel.checkUserData() },
                visualTransformation = PasswordVisualTransformation()
            )
            LoginButton(onClick = { appViewModel.checkUserData() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoField(
    placeholder: String,
    onUserValueChanged: (String) -> Unit,
    isValueWrong: Boolean,
    onKeyboardDone: () -> Unit,
    userValue: String,
    visualTransformation : VisualTransformation,
) {
    OutlinedTextField(
        value = userValue,
        onValueChange = onUserValueChanged,
        singleLine = true,
        shape = shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {
            if(isValueWrong) {
                Text(stringResource(id = R.string.wrong_value))
            } else {
                Text(placeholder)
            }
        },
        isError = isValueWrong,
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
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.author),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(4.dp)
            )
            Text(
                text = stringResource(id = R.string.copyrights),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(4.dp)
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


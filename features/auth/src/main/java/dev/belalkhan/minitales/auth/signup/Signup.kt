package dev.belalkhan.minitales.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.belalkhan.minitales.auth.R
import dev.belalkhan.minitales.theme.MiniTalesTheme
import dev.belalkhan.minitales.theme.components.AppTextField
import dev.belalkhan.minitales.theme.components.MiniTalesPreview

@Composable
internal fun SignupScreen(
    viewModel: SignupViewModel,
    onAuthSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState.value) {
        SignupUiState.Authenticated -> {
            onAuthSuccess()
        }

        is SignupUiState.Default -> {
            Signup(
                uiState = state,
                onEvent = { event ->
                    viewModel.onEvent(event)
                    if (event is SignupUiEvent.Login) {
                        onNavigateToLogin()
                    }
                },
            )
        }
    }
}

@Composable
internal fun Signup(
    uiState: SignupUiState.Default,
    onEvent: (event: SignupUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 32.dp),
            painter = painterResource(id = R.drawable.mini_tales),
            contentDescription = "mini tales",
        )

        AppTextField(
            value = uiState.fullName,
            label = R.string.full_name,
            hint = "John Doe",
            error = uiState.fullNameError,
            leadingIcon = Icons.Filled.Person,
            onValueChanged = { onEvent(SignupUiEvent.FullNameChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.email,
            label = R.string.email,
            hint = "yourname@domain.com",
            error = uiState.emailError,
            leadingIcon = Icons.Filled.Email,
            onValueChanged = { onEvent(SignupUiEvent.EmailChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.password,
            label = R.string.password,
            hint = "your password",
            error = uiState.passwordError,
            leadingIcon = Icons.Filled.Lock,
            isPasswordField = true,
            onValueChanged = { onEvent(SignupUiEvent.PasswordChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.passwordConfirm,
            label = R.string.confirm_password,
            hint = "same password again",
            error = uiState.passwordConfirmError,
            leadingIcon = Icons.Filled.Lock,
            isPasswordField = true,
            onValueChanged = { onEvent(SignupUiEvent.ConfirmPasswordChanged(it)) },
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            uiState.signupError?.let { error ->
                Text(
                    text = stringResource(error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(SignupUiEvent.Signup)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "login",
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .clickable { onEvent(SignupUiEvent.Login) },
            text = stringResource(R.string.already_have_account),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            text = stringResource(R.string.agree_to_terms),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@MiniTalesPreview
@Composable
private fun PreviewSignupScreen() {
    MiniTalesTheme {
        Surface {
            Signup(SignupUiState.Default()) {}
        }
    }
}

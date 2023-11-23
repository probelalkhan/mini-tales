package dev.belalkhan.minitales.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
fun LoginScreen(
    viewModel: LoginViewModel,
    onAuthSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState.value) {
        LoginUiState.Authenticated -> {
            LaunchedEffect(Unit) { onAuthSuccess() }
        }

        is LoginUiState.NotAuthenticated -> {
            Login(
                uiState = state,
                onEvent = { event ->
                    viewModel.onEvent(event)
                    if (event is LoginUiEvent.Signup) {
                        onNavigateToSignup()
                    }
                },
            )
        }

        is LoginUiState.AuthenticationError -> {
            // @Todo display error message
        }
    }
}

@Composable
fun Login(
    uiState: LoginUiState.NotAuthenticated,
    onEvent: (event: LoginUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Icon(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 42.dp, bottom = 32.dp),
            painter = painterResource(id = R.drawable.mini_tales),
            contentDescription = "mini tales",
        )

        AppTextField(
            value = uiState.email,
            label = R.string.email,
            hint = "yourname@domain.com",
            error = uiState.emailError,
            leadingIcon = Icons.Filled.Email,
            onValueChanged = { onEvent(LoginUiEvent.EmailChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.password,
            label = R.string.password,
            hint = "your password",
            error = uiState.passwordError,
            leadingIcon = Icons.Filled.Lock,
            isPasswordField = true,
            onValueChanged = { onEvent(LoginUiEvent.PasswordChanged(it)) },
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }
            uiState.loginError?.let { error ->
                Text(
                    text = stringResource(error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f),
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = stringResource(R.string.click_here_to_reset),
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                onClick = {
                    onEvent(LoginUiEvent.Login)
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "login",
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .clickable { onEvent(LoginUiEvent.Signup) },
            text = stringResource(R.string.dont_have_account),
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
fun LoginScreenPreview() {
    MiniTalesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Login(LoginUiState.NotAuthenticated()) {}
        }
    }
}

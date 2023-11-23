package dev.belalkhan.minitales.auth.login

import androidx.annotation.StringRes

sealed class LoginUiState {

    data object Authenticated : LoginUiState()

    data class AuthenticationError(val message: String) : LoginUiState()

    data class NotAuthenticated(
        val email: String = "",
        @StringRes val emailError: Int? = null,

        val password: String = "",
        @StringRes val passwordError: Int? = null,

        val isLoading: Boolean = false,

        @StringRes val loginError: Int? = null,
    ) : LoginUiState()
}

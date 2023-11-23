package dev.belalkhan.minitales.auth.login

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    data object Login : LoginUiEvent()
    data object ForgetPassword : LoginUiEvent()
    data object Signup : LoginUiEvent()
}

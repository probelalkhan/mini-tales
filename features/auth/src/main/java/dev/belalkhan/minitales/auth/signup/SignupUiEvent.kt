package dev.belalkhan.minitales.auth.signup

sealed class SignupUiEvent {
    data class FullNameChanged(val fullName: String) : SignupUiEvent()
    data class EmailChanged(val email: String) : SignupUiEvent()
    data class PasswordChanged(val password: String) : SignupUiEvent()
    data class ConfirmPasswordChanged(val password: String) : SignupUiEvent()
    data object Signup : SignupUiEvent()
    data object Login : SignupUiEvent()
}

package dev.belalkhan.minitales.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.minitales.auth.R
import dev.belalkhan.minitales.auth.domain.LoginUseCase
import dev.belalkhan.minitales.auth.validators.AuthParams
import dev.belalkhan.minitales.auth.validators.ValidatorFactory
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.ResourceError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validatorFactory: ValidatorFactory,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.NotAuthenticated())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.EmailChanged -> updateState { it.copy(email = uiEvent.email) }
            is LoginUiEvent.PasswordChanged -> updateState { it.copy(password = uiEvent.password) }
            LoginUiEvent.Login -> {
                if (areInputsValid()) {
                    login()
                }
            }

            else -> {
                Unit
            }
        }
    }

    private fun updateState(update: (LoginUiState.NotAuthenticated) -> LoginUiState.NotAuthenticated) {
        _uiState.value = (_uiState.value as? LoginUiState.NotAuthenticated)?.let(update)
            ?: _uiState.value
    }

    private fun areInputsValid(): Boolean {
        val ui = (_uiState.value as? LoginUiState.NotAuthenticated) ?: return false
        val emailError = validatorFactory.get(AuthParams.EMAIL).validate(ui.email)
        val passwordError = validatorFactory.get(AuthParams.PASSWORD).validate(ui.password)
        val hasError = listOf(emailError, passwordError).any { !it.isValid }
        _uiState.value = ui.copy(
            emailError = emailError.errorMessage,
            passwordError = passwordError.errorMessage,
        )
        return !hasError
    }

    private fun login() = viewModelScope.launch {
        val ui = (_uiState.value as? LoginUiState.NotAuthenticated) ?: return@launch
        _uiState.value = LoginUiState.NotAuthenticated(isLoading = true)
        _uiState.value = when (val loginResult = loginUseCase.invoke(ui.email, ui.password)) {
            is Resource.Success -> LoginUiState.Authenticated
            is Resource.Error -> LoginUiState.NotAuthenticated(loginError = getError(loginResult))
        }
    }

    private fun getError(loginError: Resource.Error): Int {
        return when (loginError.e) {
            ResourceError.UNAUTHORIZED -> R.string.invalid_email_password
            ResourceError.SERVICE_UNAVAILABLE -> R.string.service_unavailable
            ResourceError.UNKNOWN -> R.string.unknown_error
        }
    }
}

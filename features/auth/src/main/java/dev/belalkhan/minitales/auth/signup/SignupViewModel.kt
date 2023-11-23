package dev.belalkhan.minitales.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.minitales.auth.R
import dev.belalkhan.minitales.auth.domain.SignupUseCase
import dev.belalkhan.minitales.auth.validators.AuthParams
import dev.belalkhan.minitales.auth.validators.ValidatorFactory
import dev.belalkhan.minitales.common.domain.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val validatorFactory: ValidatorFactory,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Default())
    val uiState: StateFlow<SignupUiState> = _uiState

    fun onEvent(uiEvent: SignupUiEvent) {
        when (uiEvent) {
            is SignupUiEvent.FullNameChanged -> updateState { it.copy(fullName = uiEvent.fullName) }
            is SignupUiEvent.EmailChanged -> updateState { it.copy(email = uiEvent.email) }
            is SignupUiEvent.PasswordChanged -> updateState { it.copy(password = uiEvent.password) }
            is SignupUiEvent.ConfirmPasswordChanged -> updateState { it.copy(passwordConfirm = uiEvent.password) }
            SignupUiEvent.Signup -> {
                if (areInputsValid()) {
                    signup()
                }
            }

            else -> {
                Unit
            }
        }
    }

    private fun updateState(update: (SignupUiState.Default) -> SignupUiState.Default) {
        _uiState.value = (_uiState.value as? SignupUiState.Default)?.let(update)
            ?: _uiState.value
    }

    private fun areInputsValid(): Boolean {
        val ui = (_uiState.value as? SignupUiState.Default) ?: return false
        val fullNameError = validatorFactory.get(AuthParams.FULL_NAME).validate(ui.fullName)
        val emailError = validatorFactory.get(AuthParams.EMAIL).validate(ui.email)
        val passwordError = validatorFactory.get(AuthParams.PASSWORD).validate(ui.password)
        val confirmPasswordError =
            validatorFactory.get(AuthParams.PASSWORD).validate(ui.passwordConfirm)
        val passwordMatchError =
            if (confirmPasswordError.isValid && ui.passwordConfirm != ui.password) {
                R.string.password_match_error
            } else {
                null
            }

        val hasError = listOf(
            fullNameError,
            emailError,
            passwordError,
            confirmPasswordError,
        ).any { !it.isValid } || passwordMatchError != null

        _uiState.value = ui.copy(
            fullNameError = fullNameError.errorMessage,
            emailError = emailError.errorMessage,
            passwordError = passwordError.errorMessage,
            passwordConfirmError = confirmPasswordError.errorMessage ?: passwordMatchError,
        )
        return !hasError
    }

    private fun signup() = viewModelScope.launch {
        val ui = (_uiState.value as? SignupUiState.Default) ?: return@launch
        _uiState.value = SignupUiState.Default(isLoading = true)
        _uiState.value = when (signupUseCase.invoke(ui.fullName, ui.email, ui.password)) {
            is Resource.Error -> SignupUiState.Default(signupError = R.string.unknown_error)
            is Resource.Success -> SignupUiState.Authenticated
        }
    }
}

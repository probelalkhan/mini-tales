package dev.belalkhan.minitales.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.usecases.UserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: UserDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Splash())
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() = viewModelScope.launch {
        _uiState.value = SplashUiState.Splash(isLoading = true)
        when (splashUseCase.invoke()) {
            is Resource.Error -> _uiState.value = SplashUiState.Splash(moveToLogin = true)
            is Resource.Success -> _uiState.value = SplashUiState.Authenticated
        }
    }
}

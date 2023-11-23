package dev.belalkhan.minitales.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.usecases.LogoutUseCase
import dev.belalkhan.minitales.common.domain.usecases.UserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataUseCase: UserDataUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        getUserDetail()
    }

    private fun getUserDetail() = viewModelScope.launch {
        when (val user = userDataUseCase.invoke()) {
            is Resource.Error -> _uiState.value = ProfileUiState.Error
            is Resource.Success -> _uiState.value = ProfileUiState.Profile(user.result)
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
    }
}

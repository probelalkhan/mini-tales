package dev.belalkhan.minitales.profile

import dev.belalkhan.minitales.common.domain.models.User

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data object Error : ProfileUiState()
    data class Profile(val user: User) : ProfileUiState()
}

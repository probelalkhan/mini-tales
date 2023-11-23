package dev.belalkhan.minitales.home.home

import dev.belalkhan.minitales.common.domain.models.Story

data class HomeTabUiState(
    val drafts: List<Story> = emptyList(),
)

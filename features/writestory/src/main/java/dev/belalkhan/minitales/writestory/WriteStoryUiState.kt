package dev.belalkhan.minitales.writestory

import dev.belalkhan.minitales.common.domain.models.Story

internal data class WriteStoryUiState(
    val error: Int? = null,
    val title: String = "",
    val story: Story? = null,
    val draftSaved: Boolean = false,
)

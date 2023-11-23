package dev.belalkhan.minitales.writestory

internal sealed class WriteStoryUiEvent {
    data class SaveDraft(val title: String, val content: String) : WriteStoryUiEvent()
    data class TitleChanged(val title: String) : WriteStoryUiEvent()
}

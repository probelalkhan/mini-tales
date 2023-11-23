package dev.belalkhan.minitales.writestory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.ResourceError
import dev.belalkhan.minitales.writestory.domain.SaveStoryDraftUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WriteStoryViewModel @Inject constructor(
    private val useCase: SaveStoryDraftUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WriteStoryUiState())
    val uiState: StateFlow<WriteStoryUiState> = _uiState

    fun onEvent(event: WriteStoryUiEvent) {
        when (event) {
            is WriteStoryUiEvent.SaveDraft -> {
                saveDraft(event.title, event.content)
            }

            is WriteStoryUiEvent.TitleChanged ->
                _uiState.value =
                    _uiState.value.copy(title = event.title)
        }
    }

    private fun saveDraft(title: String, content: String) = viewModelScope.launch {
        _uiState.value =
            when (
                val result =
                    useCase.invoke(title, content, _uiState.value.story?.id)
            ) {
                is Resource.Error -> WriteStoryUiState(error = getError(result))
                is Resource.Success -> {
                    WriteStoryUiState(
                        title = result.result.title,
                        story = result.result,
                        draftSaved = true,
                    )
                }
            }
    }

    fun resetErrorSuccess() {
        _uiState.value = _uiState.value.copy(error = null, draftSaved = false)
    }

    private fun getError(loginError: Resource.Error): Int {
        return when (loginError.e) {
            ResourceError.UNAUTHORIZED -> R.string.invalid_email_password
            ResourceError.SERVICE_UNAVAILABLE -> R.string.service_unavailable
            ResourceError.UNKNOWN -> R.string.unknown_error
        }
    }
}

package dev.belalkhan.minitales.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.home.domain.MyDraftStoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val useCase: MyDraftStoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeTabUiState())
    val uiState: StateFlow<HomeTabUiState> = _uiState

    init {
        getDraftStories()
    }

    private fun getDraftStories() = viewModelScope.launch {
        _uiState.value = when (val result = useCase.invoke()) {
            is Resource.Error -> HomeTabUiState()
            is Resource.Success -> HomeTabUiState(result.result)
        }
    }
}

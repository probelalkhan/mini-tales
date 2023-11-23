package dev.belalkhan.minitales.writestory.domain

import dev.belalkhan.minitales.common.domain.mappers.StoryMapper
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.Story
import dev.belalkhan.minitales.common.domain.toResourceError
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.storage.SessionHandler
import dev.belalkhan.minitales.writestory.data.StoryRequest
import dev.belalkhan.minitales.writestory.data.WriteStoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveStoryDraftUseCase @Inject constructor(
    private val repository: WriteStoryRepository,
    private val sessionHandler: SessionHandler,
    private val mapper: StoryMapper,
) {
    suspend fun invoke(title: String, content: String, id: Int? = null): Resource<Story> {
        val userId = sessionHandler.getCurrentUser().first().id
        val request = StoryRequest(userId, title, content, true)
        return when (val result = repository.saveDraft(request, id)) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> Resource.Success(mapper.map(result.result.data))
        }
    }
}

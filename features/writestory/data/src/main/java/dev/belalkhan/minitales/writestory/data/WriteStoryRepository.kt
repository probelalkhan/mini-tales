package dev.belalkhan.minitales.writestory.data

import dev.belalkhan.minitales.common.data.models.StoryApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Response

interface WriteStoryRepository {
    suspend fun saveDraft(
        request: StoryRequest,
        id: Int? = null,
    ): NetworkResult<Response<StoryApiModel>>

    suspend fun publish(id: Int): NetworkResult<Response<Nothing>>
}

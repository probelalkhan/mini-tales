package dev.belalkhan.minitales.writestory.data

import dev.belalkhan.minitales.common.data.models.StoryApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Response
import dev.belalkhan.minitales.network.http.RequestHandler
import javax.inject.Inject

private const val ADD_STORY_DRAFT = "story/add"
private const val UPDATE_STORY_DRAFT = "story/update"

class WriteStoryRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
) : WriteStoryRepository {

    override suspend fun saveDraft(
        request: StoryRequest,
        id: Int?,
    ): NetworkResult<Response<StoryApiModel>> {
        return if (id != null) {
            requestHandler.put(
                urlPathSegments = listOf(UPDATE_STORY_DRAFT, id),
                body = request,
            )
        } else {
            requestHandler.post(
                urlPathSegments = listOf(ADD_STORY_DRAFT),
                body = request,
            )
        }
    }

    override suspend fun publish(id: Int): NetworkResult<Response<Nothing>> {
        TODO("Not yet implemented")
    }
}

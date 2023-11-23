package dev.belalkhan.minitales.home.data

import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.http.RequestHandler
import javax.inject.Inject

private const val STORY_DRAFTS = "story/my"

class StoryRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
) : StoryRepository {

    override suspend fun getDrafts(page: Int): NetworkResult<PaginatedDraftStory> {
        return requestHandler.get(
            urlPathSegments = listOf(STORY_DRAFTS, page),
            queryParams = mapOf("is_draft" to true),
        )
    }
}

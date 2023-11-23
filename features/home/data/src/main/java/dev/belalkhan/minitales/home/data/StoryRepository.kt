package dev.belalkhan.minitales.home.data

import dev.belalkhan.minitales.common.data.models.StoryApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Paged
import dev.belalkhan.minitales.network.Response

typealias PaginatedDraftStory = Response<Paged<StoryApiModel>>

interface StoryRepository {
    suspend fun getDrafts(page: Int): NetworkResult<PaginatedDraftStory>
}

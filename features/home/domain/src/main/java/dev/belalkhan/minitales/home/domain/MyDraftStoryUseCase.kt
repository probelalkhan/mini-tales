package dev.belalkhan.minitales.home.domain

import dev.belalkhan.minitales.common.data.models.StoryApiModel
import dev.belalkhan.minitales.common.domain.mappers.StoryMapper
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.Story
import dev.belalkhan.minitales.common.domain.toResourceError
import dev.belalkhan.minitales.home.data.StoryRepository
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Paged
import javax.inject.Inject

class MyDraftStoryUseCase @Inject constructor(
    private val repository: StoryRepository,
    private val mapper: StoryMapper,
) {
    suspend fun invoke(page: Int = 0): Resource<List<Story>> {
        return when (val result = repository.getDrafts(page)) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> getResult(result.result.data)
        }
    }

    private fun getResult(data: Paged<StoryApiModel>): Resource.Success<List<Story>> {
        return Resource.Success(data.data.map { mapper.map(it) })
    }
}

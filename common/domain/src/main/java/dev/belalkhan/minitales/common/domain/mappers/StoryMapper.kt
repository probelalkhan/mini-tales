package dev.belalkhan.minitales.common.domain.mappers

import dev.belalkhan.minitales.common.data.models.StoryApiModel
import dev.belalkhan.minitales.common.domain.models.Story
import dev.belalkhan.minitales.common.utils.Mapper
import javax.inject.Inject

class StoryMapper @Inject constructor() : Mapper<StoryApiModel, Story> {

    override fun map(from: StoryApiModel): Story {
        return Story(
            id = from.id,
            title = from.title,
            content = from.content,
            createdAt = from.createdAt,
            isDraft = from.isDraft,
        )
    }
}

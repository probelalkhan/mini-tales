package dev.belalkhan.minitales.writestory.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryRequest(
    @SerialName("userId")
    val userId: Int,

    @SerialName("title")
    val title: String,

    @SerialName("content")
    val content: String,

    @SerialName("isDraft")
    val isDraft: Boolean,
)

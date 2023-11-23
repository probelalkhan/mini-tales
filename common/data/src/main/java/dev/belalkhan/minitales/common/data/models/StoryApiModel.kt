package dev.belalkhan.minitales.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryApiModel(
    @SerialName("content")
    val content: String,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("id")
    val id: Int,

    @SerialName("isDraft")
    val isDraft: Boolean,

    @SerialName("title")
    val title: String,
)

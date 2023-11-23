package dev.belalkhan.minitales.network

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val data: T,
    val message: String? = null,
)

package dev.belalkhan.minitales.auth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSignupRequest(
    @SerialName("fullName")
    val fullName: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("avatar")
    val avatar: String,
)

package dev.belalkhan.minitales.auth.data

import dev.belalkhan.minitales.common.data.models.UserApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Response

interface AuthRepository {
    suspend fun login(request: UserLoginRequest): NetworkResult<Response<UserApiModel>>
    suspend fun signup(request: UserSignupRequest): NetworkResult<Response<UserApiModel>>
}

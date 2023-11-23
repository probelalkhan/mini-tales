package dev.belalkhan.minitales.common.data.repositories

import dev.belalkhan.minitales.common.data.models.UserApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Response

interface UserRepository {
    suspend fun user(): NetworkResult<Response<UserApiModel>>
}

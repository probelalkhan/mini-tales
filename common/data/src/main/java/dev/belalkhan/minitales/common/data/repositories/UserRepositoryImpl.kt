package dev.belalkhan.minitales.common.data.repositories

import dev.belalkhan.minitales.common.data.models.UserApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Response
import dev.belalkhan.minitales.network.http.RequestHandler
import javax.inject.Inject

private const val USER = "user"

class UserRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
) : UserRepository {

    override suspend fun user(): NetworkResult<Response<UserApiModel>> =
        requestHandler.get(urlPathSegments = listOf(USER))
}

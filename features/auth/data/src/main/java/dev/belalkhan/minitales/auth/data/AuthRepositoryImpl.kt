package dev.belalkhan.minitales.auth.data

import dev.belalkhan.minitales.common.data.models.UserApiModel
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.Response
import dev.belalkhan.minitales.network.http.RequestHandler
import javax.inject.Inject

private const val LOGIN = "auth/login"
private const val SIGNUP = "auth/register"

class AuthRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
) : AuthRepository {

    override suspend fun login(request: UserLoginRequest): NetworkResult<Response<UserApiModel>> =
        requestHandler.post(
            urlPathSegments = listOf(LOGIN),
            body = request,
        )

    override suspend fun signup(request: UserSignupRequest): NetworkResult<Response<UserApiModel>> =
        requestHandler.post(
            urlPathSegments = listOf(SIGNUP),
            body = request,
        )
}

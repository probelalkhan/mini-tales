package dev.belalkhan.minitales.auth.domain

import dev.belalkhan.minitales.auth.data.AuthRepository
import dev.belalkhan.minitales.auth.data.UserLoginRequest
import dev.belalkhan.minitales.common.domain.mappers.UserMapper
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.User
import dev.belalkhan.minitales.common.domain.toResourceError
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.storage.SessionHandler
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val sessionHandler: SessionHandler,
    private val mapper: UserMapper,
) {

    suspend fun invoke(email: String, password: String): Resource<User> {
        val request = UserLoginRequest(email, password)
        return when (val result = repository.login(request)) {
            is NetworkResult.Error -> {
                result.toResourceError()
            }

            is NetworkResult.Success -> {
                sessionHandler.setCurrentUser(result.result.data.id, result.result.data.authToken)
                Resource.Success(mapper.map(result.result.data))
            }
        }
    }
}

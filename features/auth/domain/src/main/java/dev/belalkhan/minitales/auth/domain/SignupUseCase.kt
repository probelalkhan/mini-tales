package dev.belalkhan.minitales.auth.domain

import dev.belalkhan.minitales.auth.data.AuthRepository
import dev.belalkhan.minitales.auth.data.UserSignupRequest
import dev.belalkhan.minitales.common.domain.mappers.UserMapper
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.User
import dev.belalkhan.minitales.common.domain.toResourceError
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.storage.SessionHandler
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val sessionHandler: SessionHandler,
    private val mapper: UserMapper,
) {

    suspend fun invoke(fullName: String, email: String, password: String): Resource<User> {
        val request = UserSignupRequest(fullName, email, password, "dummy-avatar")
        return when (val result = repository.signup(request)) {
            is NetworkResult.Error -> {
                result.toResourceError()
            }

            is NetworkResult.Success -> {
                val (userId, authKey) = Pair(result.result.data.id, result.result.data.authToken)
                sessionHandler.setCurrentUser(userId, authKey)
                Resource.Success(mapper.map(result.result.data))
            }
        }
    }
}

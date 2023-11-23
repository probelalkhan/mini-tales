package dev.belalkhan.minitales.common.domain.usecases

import dev.belalkhan.minitales.common.data.repositories.UserRepository
import dev.belalkhan.minitales.common.domain.mappers.UserMapper
import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.User
import dev.belalkhan.minitales.common.domain.toResourceError
import dev.belalkhan.minitales.network.NetworkResult
import javax.inject.Inject

class UserDataUseCase @Inject constructor(
    private val repository: UserRepository,
    private val mapper: UserMapper,
) {

    suspend fun invoke(): Resource<User> {
        return when (val result = repository.user()) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> Resource.Success(mapper.map(result.result.data))
        }
    }
}

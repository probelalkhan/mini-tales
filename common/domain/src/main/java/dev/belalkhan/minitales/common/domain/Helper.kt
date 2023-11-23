package dev.belalkhan.minitales.common.domain

import dev.belalkhan.minitales.common.domain.models.Resource
import dev.belalkhan.minitales.common.domain.models.ResourceError
import dev.belalkhan.minitales.network.NetworkException
import dev.belalkhan.minitales.network.NetworkResult

fun NetworkResult.Error<*>.toResourceError(): Resource.Error {
    return when (exception) {
        is NetworkException.NotFoundException -> Resource.Error(ResourceError.SERVICE_UNAVAILABLE)
        is NetworkException.UnauthorizedException -> Resource.Error(ResourceError.UNAUTHORIZED)
        is NetworkException.UnknownException -> Resource.Error(ResourceError.UNKNOWN)
    }
}

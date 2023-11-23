package dev.belalkhan.minitales.common.domain.models

sealed class Resource<out T> {
    data class Error(val e: ResourceError) : Resource<Nothing>()
    data class Success<R>(val result: R) : Resource<R>()
}

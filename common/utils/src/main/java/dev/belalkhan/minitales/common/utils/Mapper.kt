package dev.belalkhan.minitales.common.utils

interface Mapper<F, T> {
    fun map(from: F): T
}

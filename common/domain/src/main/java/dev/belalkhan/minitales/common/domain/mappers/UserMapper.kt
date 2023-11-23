package dev.belalkhan.minitales.common.domain.mappers

import dev.belalkhan.minitales.common.data.models.UserApiModel
import dev.belalkhan.minitales.common.domain.models.User
import dev.belalkhan.minitales.common.utils.Mapper
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserApiModel, User> {
    override fun map(from: UserApiModel): User =
        User(
            avatar = from.avatar,
            email = from.email,
            createdAt = from.createdAt,
            fullName = from.fullName,
            id = from.id,
        )
}

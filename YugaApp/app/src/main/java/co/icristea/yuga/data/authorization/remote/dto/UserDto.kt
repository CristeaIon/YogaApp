package co.icristea.yuga.data.authorization.remote.dto

import co.icristea.yuga.domain.authorization.model.User

data class UserDto(
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val token: String,
    val refreshToken: String,
) {
    fun toUser(): User {
        return User(
            fullName = fullName,
            email = email,
            phone = phone
        )
    }
}

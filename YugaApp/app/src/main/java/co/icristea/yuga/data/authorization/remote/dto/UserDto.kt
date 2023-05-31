package co.icristea.yuga.data.authorization.remote.dto

import co.icristea.yuga.domain.authorization.model.User

data class UserDto(
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val token: String,
    val refreshToken: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toUser(): User {
        return User(
            fullName = fullName,
            email = email,
            phone = phone,
            token = token,
            refreshToken = refreshToken,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}

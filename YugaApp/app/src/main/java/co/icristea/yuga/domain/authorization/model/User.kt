package co.icristea.yuga.domain.authorization.model

data class User(
    val fullName: String,
    val email: String,
    val phone: String,
    val token: String,
    val refreshToken: String,
    val createdAt: String,
    val updatedAt: String,
)

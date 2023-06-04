package co.icristea.yuga.data.authorization.remote.dto

data class RestorePasswordDto(
    val id: String,
    val contact: String,
    val sendStatus: String,
    val sendTime: String,
)

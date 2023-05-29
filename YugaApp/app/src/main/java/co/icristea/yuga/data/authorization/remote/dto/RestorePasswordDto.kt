package co.icristea.yuga.data.authorization.remote.dto

data class RestorePasswordDto(
    val restoreToken: String,
    val restoreDate: String,
    val liveTime: Int,
)

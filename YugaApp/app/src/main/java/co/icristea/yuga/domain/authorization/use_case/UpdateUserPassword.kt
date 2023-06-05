package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.data.authorization.remote.dto.RestorePasswordDto
import co.icristea.yuga.data.authorization.remote.dto.UserDto
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateUserPassword(
    private val repository: IAuthorizationRepository
) {
    operator fun invoke(
        password: String,
        email: String,
    ): Flow<Response<UserDto>> {
        if (password.isBlank()) {
            return flow {}
        }
        val body = mapOf(
            "password" to password, "email" to email,
        )

        return repository.updatePassword(body)
    }
}
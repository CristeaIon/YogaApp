package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.data.authorization.remote.dto.RestorePasswordDto
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreUserPassword(
    private val repository: IAuthorizationRepository
) {
    operator fun invoke(
        email: String,
    ): Flow<Response<RestorePasswordDto>> {
        if (email.isBlank()) {
            return flow {}
        }

        return repository.restorePassword(email)
    }
}
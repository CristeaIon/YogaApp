package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.data.authorization.remote.dto.ValidationDto
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateCode(
    private val repository: IAuthorizationRepository
) {
    operator fun invoke(
        id: String,
        contact: String,
        code: String,
    ): Flow<Response<ValidationDto>> {
        if (code.isBlank() || code.length < 4) {
            return flow {}
        }
        val body = mapOf(
            "id" to id,
            "contact" to contact,
            "code" to code
        )
        return repository.verifyCode(body)
    }
}
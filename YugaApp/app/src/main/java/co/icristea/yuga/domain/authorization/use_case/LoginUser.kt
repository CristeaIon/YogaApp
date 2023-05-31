package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUser(
    private val repository: IAuthorizationRepository
) {
    operator fun invoke(

        email: String,

        password: String
    ): Flow<Response<User>> {
        if (email.isBlank() || password.isBlank()) {
            return flow {}
        }
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        return repository.loginUser(body)
    }
}
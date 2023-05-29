package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignupUser(
    private val repository: IAuthorizationRepository
) {
    operator fun invoke(
        fullName: String,
        email: String,
        phone: String,
        password: String
    ): Flow<Response<User>> {
        if (fullName.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
            return flow {}
        }
        val body = mapOf(
            "fullName" to fullName,
            "email" to email,
            "phone" to phone,
            "password" to password
        )
        return repository.signupUser(body)
    }
}
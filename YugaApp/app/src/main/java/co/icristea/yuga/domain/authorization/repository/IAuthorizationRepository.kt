package co.icristea.yuga.domain.authorization.repository

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.data.authorization.remote.dto.RestorePasswordDto
import co.icristea.yuga.data.authorization.remote.dto.UserDto
import co.icristea.yuga.data.authorization.remote.dto.ValidationDto
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.use_case.RestoreUserPassword
import kotlinx.coroutines.flow.Flow

interface IAuthorizationRepository {

    fun signupUser(userData: Map<String, String>): Flow<Response<User>>

    fun loginUser(userData: Map<String, String>): Flow<Response<User>>

    fun restorePassword(email: String): Flow<Response<RestorePasswordDto>>

    fun verifyCode(body: Map<String, String>): Flow<Response<ValidationDto>>

    fun updatePassword(body: Map<String, String>): Flow<Response<UserDto>>

}
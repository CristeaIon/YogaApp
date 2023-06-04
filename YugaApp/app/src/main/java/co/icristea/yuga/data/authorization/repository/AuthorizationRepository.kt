package co.icristea.yuga.data.authorization.repository

import co.icristea.yuga.core.util.Response
import co.icristea.yuga.data.authorization.AuthorisationApi
import co.icristea.yuga.data.authorization.remote.dto.RestorePasswordDto
import co.icristea.yuga.domain.authorization.model.User
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(private val api: AuthorisationApi) :
    IAuthorizationRepository {
    override fun signupUser(userData: Map<String, String>): Flow<Response<User>> = flow {

        try {
            val userDto = api.signupUser(userData)

            emit(Response.Success(userDto.toUser()))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server, check your internet connection"))
        }
    }

    override fun loginUser(userData: Map<String, String>): Flow<Response<User>> = flow {

        try {
            val userDto = api.loginUser(userData)

            emit(Response.Success(userDto.toUser()))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server, check your internet connection"))
        }
    }

    override fun restorePassword(email: String): Flow<Response<RestorePasswordDto>> = flow {
        val body = mapOf("email" to email)

        try {
            val response = api.restorePassword(body)

            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server, check your internet connection"))
        }
    }

    override fun verifyCode(code: String): Flow<Response<Unit>> = flow {
        val body = mapOf("code" to code)

        try {
            val userDto = api.verifyCode(body)

            emit(Response.Success(Unit))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server, check your internet connection"))
        }
    }

    override fun updatePassword(password: String): Flow<Response<Unit>> = flow {
        val body = mapOf("password" to password)

        try {
            val userDto = api.updatePassword(body)

            emit(Response.Success(Unit))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server, check your internet connection"))
        }
    }
}
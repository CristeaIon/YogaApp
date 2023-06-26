package co.icristea.yuga.data.authorization

import co.icristea.yuga.data.authorization.remote.dto.RestorePasswordDto
import co.icristea.yuga.data.authorization.remote.dto.UserDto
import co.icristea.yuga.data.authorization.remote.dto.ValidationDto
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthorisationApi {

    @POST("user/signup")
    suspend fun signupUser(@Body data: Map<String, String>): UserDto

    @POST("user/login")
    suspend fun loginUser(@Body data: Map<String, String>): UserDto

    @POST("password/restore")
    suspend fun restorePassword(@Body data: Map<String, String>): RestorePasswordDto

    @POST("password/validate-code")
    suspend fun verifyCode(@Body data: Map<String, String>): ValidationDto

    @PATCH("password/update")
    suspend fun updatePassword(@Body data: Map<String, String>): UserDto

    companion object {
        const val BASE_URL = "http://192.168.1.102:9090/api/v1/"
    }
}//"http://10.0.2.2:8080/user/" emulators
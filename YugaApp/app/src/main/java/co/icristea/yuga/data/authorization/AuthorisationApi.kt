package co.icristea.yuga.data.authorization

import co.icristea.yuga.data.authorization.remote.dto.RestorePasswordDto
import co.icristea.yuga.data.authorization.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthorisationApi {

    @POST("user/signup")
    suspend fun signupUser(@Body data: Map<String,String>): UserDto

    @POST("user/login")
    suspend fun loginUser(@Body data: Map<String, String>): UserDto

    @POST("password/restore")
    suspend fun restorePassword(@Body data: Map<String, String>): RestorePasswordDto

    @POST("/password/verify-code")
    suspend fun verifyCode(@Body data: Map<String, String>)

    @PATCH("password/update")
    suspend fun updatePassword(@Body data: Map<String, String>)

    companion object {
       const val BASE_URL = "http://10.0.2.2:8080/"
    }
}//"http://10.0.2.2:8080/user/" emmulators
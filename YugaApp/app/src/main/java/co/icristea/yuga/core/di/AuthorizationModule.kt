package co.icristea.yuga.core.di

import co.icristea.yuga.data.authorization.AuthorisationApi
import co.icristea.yuga.data.authorization.repository.AuthorizationRepository
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import co.icristea.yuga.domain.authorization.use_case.SignupUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthorizationModule {

    @Provides
    @Singleton
    fun provideSignupUseCase(repository: AuthorizationRepository): SignupUser {
        return SignupUser(repository)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthorisationApi): IAuthorizationRepository {
        return AuthorizationRepository(api)
    }

    @Provides
    @Singleton
    fun provideAuthApi(): AuthorisationApi {
        return Retrofit.Builder().baseUrl(AuthorisationApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AuthorisationApi::class.java)
    }
}
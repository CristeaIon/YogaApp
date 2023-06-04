package co.icristea.yuga.core.di

import co.icristea.yuga.data.authorization.AuthorisationApi
import co.icristea.yuga.data.authorization.repository.AuthorizationRepository
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import co.icristea.yuga.domain.authorization.use_case.LoginUser
import co.icristea.yuga.domain.authorization.use_case.RestoreUserPassword
import co.icristea.yuga.domain.authorization.use_case.SignupUser
import co.icristea.yuga.domain.authorization.use_case.ValidateEmail
import co.icristea.yuga.domain.authorization.use_case.ValidateFullName
import co.icristea.yuga.domain.authorization.use_case.ValidatePassword
import co.icristea.yuga.domain.authorization.use_case.ValidatePhone
import co.icristea.yuga.domain.authorization.use_case.ValidateRepeatedPassword
import co.icristea.yuga.domain.authorization.use_case.ValidateTerms
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
    fun provideLoginUseCase(repository: AuthorizationRepository): LoginUser {
        return LoginUser(repository)
    }

    @Provides
    @Singleton
    fun provideRestorePasswordUseCase(repository: IAuthorizationRepository): RestoreUserPassword {
        return RestoreUserPassword(repository)
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmail {
        return ValidateEmail()
    }
    @Provides
    @Singleton
    fun provideValidateFullNameUseCase(): ValidateFullName {
        return ValidateFullName()
    }
    @Provides
    @Singleton
    fun provideValidatePhoneUseCase(): ValidatePhone {
        return ValidatePhone()
    }
    @Provides
    @Singleton
    fun provideValidateTermsUseCase(): ValidateTerms {
        return ValidateTerms()
    }
    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(): ValidatePassword {
        return ValidatePassword()
    }
    @Provides
    @Singleton
    fun provideValidateRepeatedPasswordUseCase(): ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
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
package co.icristea.yuga.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import co.icristea.yuga.data.authorization.AuthorisationApi
import co.icristea.yuga.data.authorization.repository.AuthorizationRepository
import co.icristea.yuga.data.local.Storage
import co.icristea.yuga.domain.authorization.repository.IAuthorizationRepository
import co.icristea.yuga.domain.authorization.use_case.LoginUser
import co.icristea.yuga.domain.authorization.use_case.RestoreUserPassword
import co.icristea.yuga.domain.authorization.use_case.SignupUser
import co.icristea.yuga.domain.authorization.use_case.UpdateUserPassword
import co.icristea.yuga.domain.authorization.use_case.ValidateCode
import co.icristea.yuga.domain.authorization.use_case.ValidateEmail
import co.icristea.yuga.domain.authorization.use_case.ValidateFullName
import co.icristea.yuga.domain.authorization.use_case.ValidatePassword
import co.icristea.yuga.domain.authorization.use_case.ValidatePhone
import co.icristea.yuga.domain.authorization.use_case.ValidateRepeatedPassword
import co.icristea.yuga.domain.authorization.use_case.ValidateTerms
import co.icristea.yuga.domain.storage.IStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideValidateCodeUseCase(repository: IAuthorizationRepository): ValidateCode {
        return ValidateCode(repository)
    }

    @Provides
    @Singleton
    fun provideUpdatePasswordUseCase(repository: IAuthorizationRepository): UpdateUserPassword {
        return UpdateUserPassword(repository)
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
    fun provideStorage(dataStore: DataStore<Preferences>): IStorage {
        return Storage(dataStore)
    }

    @Provides
    @Singleton
    fun provideDataStorage(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context, "restore")),
            scope = CoroutineScope(
                Dispatchers.IO + SupervisorJob()
            ),
            produceFile = { context.preferencesDataStoreFile("restore") }
        )
    }


    @Provides
    @Singleton
    fun provideAuthApi(): AuthorisationApi {
        return Retrofit.Builder().baseUrl(AuthorisationApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AuthorisationApi::class.java)
    }
}

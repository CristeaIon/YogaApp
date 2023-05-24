package co.icristea.yuga.core.di

import co.icristea.yuga.data.onboarding.OnboardingRepository
import co.icristea.yuga.domain.onboarding.IOnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideOnboardingRepository(): IOnboardingRepository {
     return OnboardingRepository()
    }
}
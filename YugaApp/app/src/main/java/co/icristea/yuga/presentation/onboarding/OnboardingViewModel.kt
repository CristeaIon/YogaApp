package co.icristea.yuga.presentation.onboarding

import androidx.lifecycle.ViewModel
import co.icristea.yuga.domain.onboarding.IOnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class OnboardingViewModel @Inject constructor(
    private val repository: IOnboardingRepository
): ViewModel() {

}
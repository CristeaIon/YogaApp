package co.icristea.yuga.domain.onboarding

interface IOnboardingRepository {
    fun getNextStep()
    fun skipOnboarding()
}
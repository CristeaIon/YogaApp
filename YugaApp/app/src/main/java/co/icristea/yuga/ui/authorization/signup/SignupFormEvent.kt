package co.icristea.yuga.ui.authorization.signup

sealed class SignupFormEvent {
    data class FullNameChanged(val fullName: String) : SignupFormEvent()
    data class EmailChanged(val email: String) : SignupFormEvent()
    data class PasswordChanged(val password: String) : SignupFormEvent()
    data class PhoneChanged(val phone: String) : SignupFormEvent()
    data class TermsChanged(val isAccepted: Boolean) : SignupFormEvent()
    object Submit : SignupFormEvent()
}

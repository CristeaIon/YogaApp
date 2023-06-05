package co.icristea.yuga.ui.authorization.restore.update

sealed class UpdatePasswordFormEvent {
    data class PasswordChanged(val password: String) : UpdatePasswordFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : UpdatePasswordFormEvent()
    object Submit : UpdatePasswordFormEvent()
}

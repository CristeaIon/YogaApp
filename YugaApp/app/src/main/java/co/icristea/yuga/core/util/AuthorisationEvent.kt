package co.icristea.yuga.core.util


sealed class AuthorisationEvent {
    object Success : AuthorisationEvent()
    data class Error(val message: String) : AuthorisationEvent()
}
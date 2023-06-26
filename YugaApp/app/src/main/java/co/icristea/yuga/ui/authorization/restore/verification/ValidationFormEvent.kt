package co.icristea.yuga.ui.authorization.restore.verification

sealed class ValidationFormEvent {
    data class CodeChanged(val code: String) : ValidationFormEvent()
    object SubmitCode : ValidationFormEvent()
}

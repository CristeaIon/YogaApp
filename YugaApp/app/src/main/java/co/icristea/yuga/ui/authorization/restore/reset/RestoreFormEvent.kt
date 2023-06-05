package co.icristea.yuga.ui.authorization.restore.reset

sealed class RestoreFormEvent {
    data class EmailChanged(val email: String) : RestoreFormEvent()
    object Submit : RestoreFormEvent()
}

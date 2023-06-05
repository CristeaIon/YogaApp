package co.icristea.yuga.ui.authorization.restore.verification

data class ValidationFormState(
    val code:String="",
    val codeError: String? = null,
)

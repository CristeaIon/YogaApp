package co.icristea.yuga.ui.authorization.login

data class LoginFormState(
    val email:String="",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)

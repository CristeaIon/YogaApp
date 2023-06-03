package co.icristea.yuga.ui.authorization.signup

data class SignupFormState(
    val fullName:String="",
    val fullNameError: String? = null,
    val email:String="",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val phone:String = "",
    val phoneError: String? = null,
    val termsAccepted: Boolean = false,
    val termsError: String? =null,
)

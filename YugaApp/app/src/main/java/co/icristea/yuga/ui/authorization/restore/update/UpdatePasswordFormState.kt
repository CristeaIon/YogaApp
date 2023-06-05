package co.icristea.yuga.ui.authorization.restore.update

data class UpdatePasswordFormState(
    val password:String="",
    val passwordError: String? = null,
    val repeatedPassword:String="",
    val repeatedPasswordError: String? = null,
)

package co.icristea.yuga.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ResetPassword : Screen("reset_password_screen")
    object VerificationCode : Screen("verification_code_screen")
    object NewPassword : Screen("new_password_screen")
    object PasswordChanged : Screen("password_changed_screen")
    object Home : Screen("home_screen")
}

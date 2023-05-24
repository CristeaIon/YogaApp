package co.icristea.yuga.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.icristea.yuga.presentation.authorization.CreateNewPasswordScreen
import co.icristea.yuga.presentation.authorization.LoginScreen
import co.icristea.yuga.presentation.authorization.PasswordChangedScreen
import co.icristea.yuga.presentation.authorization.ResetPasswordScreen
import co.icristea.yuga.presentation.authorization.SignupScreen
import co.icristea.yuga.presentation.authorization.VerificationCodeScreen
import co.icristea.yuga.presentation.authorization.WelcomeScreen
import co.icristea.yuga.presentation.onboarding.Onboarding
import co.icristea.yuga.presentation.splash.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Onboarding.route) {
            Onboarding(navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(navController)
        }
        composable(route = Screen.ResetPassword.route) {
            ResetPasswordScreen(navController)
        }
        composable(route = Screen.VerificationCode.route) {
            VerificationCodeScreen(navController)
        }
        composable(route = Screen.NewPassword.route) {
            CreateNewPasswordScreen(navController)
        }
        composable(route = Screen.PasswordChanged.route) {
            PasswordChangedScreen(navController)
        }
    }
}



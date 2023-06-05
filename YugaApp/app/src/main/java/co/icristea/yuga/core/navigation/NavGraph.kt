package co.icristea.yuga.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.icristea.yuga.ui.authorization.restore.update.CreateNewPasswordScreen
import co.icristea.yuga.ui.authorization.login.LoginScreen
import co.icristea.yuga.ui.composable.PasswordChangedScreen
import co.icristea.yuga.ui.authorization.restore.reset.ResetPasswordScreen
import co.icristea.yuga.ui.authorization.signup.SignupScreen
import co.icristea.yuga.ui.authorization.restore.verification.VerificationCodeScreen
import co.icristea.yuga.ui.authorization.WelcomeScreen
import co.icristea.yuga.ui.home.HomeScreen
import co.icristea.yuga.ui.onboarding.Onboarding
import co.icristea.yuga.ui.splash.SplashScreen

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
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}



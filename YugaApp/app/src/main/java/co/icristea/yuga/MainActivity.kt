package co.icristea.yuga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.core.navigation.SetupNavGraph
import androidx.hilt.navigation.compose.hiltViewModel
import co.icristea.yuga.presentation.onboarding.OnboardingViewModel
import co.icristea.yuga.ui.theme.YugaAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YugaAppTheme {
                val navController = rememberNavController()
//                val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
                SetupNavGraph(navController = navController)
            }
        }
    }
}

package co.icristea.yuga.ui.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.core.navigation.Screen
import co.icristea.yuga.ui.composable.OnboardingPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun Onboarding(navController: NavController) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third,
    )

    val pagerState = rememberPagerState(pageCount = 3)
    val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {

        onboardingViewModel.isOnboardingSkipped().collect {
            if (it) {
                navController.navigate(Screen.Welcome.route)
            }
        }
    }


    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentPadding = PaddingValues(0.dp)
            ) {
                Column(
                    Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black),
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = {
                            navController.popBackStack()
                            onboardingViewModel.setIsOnboardingSkipped()
                            navController.navigate(Screen.Welcome.route)
                        }) {
                            Text(text = "Skip")
                        }
                        HorizontalPagerIndicator(
                            pagerState = pagerState,
                            inactiveColor = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HorizontalPager(
                modifier = Modifier.background(Color.White),
                state = pagerState,
                dragEnabled = false
            ) { position ->
                val coroutineScope = rememberCoroutineScope()
                OnboardingPage(
                    pages[position]
                ) {
                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        navController.navigate(Screen.Welcome.route)
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun OnBoardingPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun OnBoardingBlackPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}
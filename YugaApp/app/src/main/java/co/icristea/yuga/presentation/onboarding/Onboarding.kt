package co.icristea.yuga.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.core.navigation.Screen
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

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.Welcome.route)
                    }) {
                        Text(text = "Skip")
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier.padding(10.dp)
                    )
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
                state = pagerState,
                dragEnabled = false
            ) { position ->
                val coroutineScope = rememberCoroutineScope()
                OnboardingStep(
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
fun OnboardingStep(
    page: OnBoardingPage,
    onNext: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 87.dp)
                .height(300.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = page.image),
                contentDescription = "onboarding first screen"
            )
        }
        Spacer(modifier = Modifier.height(53.dp))
        Text(
            text = page.text,
            fontSize = 22.sp,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = page.title,
            fontSize = 38.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = page.message,
            Modifier.padding(horizontal = 45.dp),
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(35.dp))
        ElevatedButton(onClick = onNext) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Next")
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = "button Icon"
                )
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
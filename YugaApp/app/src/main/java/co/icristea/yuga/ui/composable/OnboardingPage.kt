package co.icristea.yuga.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.icristea.yuga.R
import co.icristea.yuga.ui.onboarding.OnBoardingPage
import co.icristea.yuga.ui.theme.BlackText


@Composable
fun OnboardingPage(
    page: OnBoardingPage,
    onNext: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(.12f))
        Image(
            modifier = Modifier.fillMaxHeight(.5f),
            painter = painterResource(id = page.image),
            contentDescription = "onboarding first screen"
        )

        Spacer(modifier = Modifier.fillMaxHeight(.1f))
        Text(
            text = page.text,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.fillMaxHeight(.1f))
        Text(
            text = page.title,
            fontSize = 38.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.fillMaxHeight(.1f))
        Text(
            text = page.message,
            Modifier.padding(horizontal = 45.dp),
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.fillMaxHeight(.25f))
        ElevatedButton(
            onClick = onNext, colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Next", color = BlackText)
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = "button Icon"
                )
            }
        }

    }
}
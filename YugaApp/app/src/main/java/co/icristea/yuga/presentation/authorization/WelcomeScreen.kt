package co.icristea.yuga.presentation.authorization

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.core.navigation.Screen
import co.icristea.yuga.ui.theme.Grey

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(146.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Spacer(modifier = Modifier.height(55.dp))
            Text(
                text = "Welcome to yoga Online class",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 50.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                WelcomeButton(
                    title = "Login",
                    textColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                WelcomeButton(
                    title = "SignUp",
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,
                    onClick = {
                        navController.navigate(Screen.Signup.route)
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.height(59.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier.width(100.dp))
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = "Or via social media", fontSize = 18.sp, color = Grey)
                Spacer(modifier = Modifier.width(15.dp))
                Divider(modifier = Modifier.width(100.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row {
                WelcomeSocialButton(id = R.drawable.facebook, onClick = {})
                Spacer(modifier = Modifier.width(12.dp))
                WelcomeSocialButton(id = R.drawable.twitter, onClick = {})
                Spacer(modifier = Modifier.width(12.dp))
                WelcomeSocialButton(id = R.drawable.gmail, onClick = {})
            }
        }
    }
}

@Composable
fun WelcomeSocialButton(
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = id),
            contentDescription = ""
        )
    }
}

@Composable
fun WelcomeButton(
    title: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        elevation = ButtonDefaults.elevation(5.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = Modifier
            .background(Color.White)
            .height(50.dp)
            .width(136.dp)
            .shadow(
                30.dp,
                RoundedCornerShape(30.dp),
                //TODO:(icristea) fix button shadow
//                false,
//                Color.Blue,
//                Color.Red

            ),
        onClick = onClick,
    ) {
        Text(
            text = title, fontSize = 20.sp, color = textColor
        )
    }
}

@Composable
@Preview()
fun WelcomePreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController)
}

//@Composable
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//fun WelcomePreviewBlackTheme() {
//    val navController = rememberNavController()
//    WelcomePage(navController)
//}
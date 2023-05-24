package co.icristea.yuga.presentation.authorization

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.core.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = GenericShape { size, _ ->
                    val rect = Rect(
                        left = -0.3f * size.width,
                        right = 1.3f * size.width,
                        top = size.height * 0.4f,
                        bottom = size.height //* .6f
                    )
                    lineTo(0f, size.height * .5f)
                    arcTo(rect, -180f, 180.0f, false)
                    lineTo(size.width, 0f)
                    close()
                }),
        )
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(85.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(190.dp)
            )
            Spacer(modifier = Modifier.height(120.dp))
            Text(text = "Login", fontSize = 36.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = "your email@gmail.com")
                },
                label = {
                    Text(text = "Email")
                },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.email), contentDescription = "")
                },
                shape = RoundedCornerShape(45.dp),
            )
            Spacer(modifier = Modifier.height(23.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = "",
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {},
                label = {
                    Text(text = "Password")
                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.eyes), contentDescription = "")
                },
                shape = RoundedCornerShape(45.dp),
            )
            Spacer(modifier = Modifier.height(23.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(45.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Login", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            ClickableText(
                style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 15.sp),
                text = AnnotatedString("Forget your password"),
                onClick = {
                    navController.navigate(Screen.ResetPassword.route)
                },
            )
            Spacer(modifier = Modifier.height(41.dp))
            Row {
                Text(text = "Don't have an account?")
                Spacer(modifier = Modifier.width(10.dp))
                ClickableText(
                    style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 15.sp),
                    text = AnnotatedString("Sign up"),
                    onClick = {
                        navController.navigate(Screen.Signup.route)
                    },
                )
            }
        }
    }
}

@Composable
@Preview
fun LoginPagePreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}
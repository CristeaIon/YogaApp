package co.icristea.yuga.ui.authorization.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.core.navigation.Screen
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.ui.composable.AuthorisationButton
import co.icristea.yuga.ui.composable.AuthorizationTextField

@Composable
fun LoginScreen(navController: NavController) {

    val loginViewModel = hiltViewModel<LoginViewModel>()
    val state = loginViewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        loginViewModel.validationEvents.collect { event ->
            when (event) {
                is AuthorisationEvent.Success -> {
                    navController.navigate(Screen.Home.route)
                }

                is AuthorisationEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            Spacer(modifier = Modifier.fillMaxHeight(.12f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(.38f)
            )
            Spacer(modifier = Modifier.fillMaxHeight(.16f))
            Text(
                text = "Login",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.fillMaxHeight(.1f))
            AuthorizationTextField(
                value = state.email,
                onChange = { loginViewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onTertiary,
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "your email@gmail.com",
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                },
                label = {
                    Text(text = "Email", color = MaterialTheme.colorScheme.tertiary)
                },
                isError = !state.emailError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.emailError ?: "", fontSize = 10.sp, color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(.02f))
            AuthorizationTextField(
                value = state.password,
                visualTransformation = PasswordVisualTransformation(),
                onChange = {
                    loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                },
                label = {
                    Text(text = "Password", color = MaterialTheme.colorScheme.tertiary)
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.eyes),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onTertiary,
                            blendMode = BlendMode.SrcIn
                        ),
                    )
                },
                isError = !state.passwordError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.passwordError ?: "", fontSize = 10.sp, color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(.02f))
            AuthorisationButton(
                title = "Login",
                textColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                loginViewModel.onEvent(LoginFormEvent.Submit)
            }
            Spacer(modifier = Modifier.height(10.dp))
            ClickableText(
                style = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 15.sp),
                text = AnnotatedString("Forget your password"),
                onClick = {
                    navController.navigate(Screen.ResetPassword.route)
                },
            )
            Spacer(modifier = Modifier.fillMaxHeight(.2f))
            Row {
                Text(
                    text = "Don't have an account?",
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(10.dp))
                ClickableText(
                    style = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp),
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



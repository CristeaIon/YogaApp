package co.icristea.yuga.ui.authorization.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
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
import co.icristea.yuga.ui.theme.HintColor
import co.icristea.yuga.ui.theme.Primary
import co.icristea.yuga.ui.theme.White

@Composable
fun SignupScreen(navController: NavController) {

    val signupViewModel = hiltViewModel<SignupViewModel>()
    val state = signupViewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        signupViewModel.validationEvents.collect { event ->
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

        SignUpBackground()
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(110.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.fillMaxHeight(.07f))
            Text(text = "Sign Up", fontSize = 36.sp, color = Color.White)
            Spacer(modifier = Modifier.fillMaxHeight(.05f))

            AuthorizationTextField(
                value = state.fullName,
                onChange = {
                    signupViewModel.onEvent(SignupFormEvent.FullNameChanged(it))
                },
                placeholder = {
                    Text(text = "Your name here", color = HintColor)
                },
                label = {
                    Text(text = "Full Name", color = White)
                },
                isError = !state.fullNameError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.fullNameError ?: "", fontSize = 10.sp, color = Color.Red)
                },
            )

            Spacer(modifier = Modifier.fillMaxHeight(.01f))
            AuthorizationTextField(
                value = state.email,
                onChange = {
                    signupViewModel.onEvent(SignupFormEvent.EmailChanged(it))
                },
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onTertiary,
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(text = "your email@gmail.com", color = HintColor)
                    }
                },
                label = {
                    Text(text = "Email", color = White)
                },
                isError = !state.emailError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.emailError ?: "", fontSize = 10.sp, color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(.01f))

            AuthorizationTextField(
                value = state.phone,
                onChange = {
                    signupViewModel.onEvent(SignupFormEvent.PhoneChanged(it))
                },
                placeholder = {
                    Text(text = "+373 699999", color = HintColor)
                },
                label = {
                    Text(text = "Phone", color = White)
                },
                isError = !state.phoneError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.phoneError ?: "", fontSize = 10.sp, color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(.01f))
            AuthorizationTextField(
                value = state.password,
                visualTransformation = PasswordVisualTransformation(),
                onChange = {
                    signupViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                },
                label = {
                    Text(text = "Password", color = White)
                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.eyes), contentDescription = "")
                },
                isError = !state.passwordError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.passwordError ?: "", fontSize = 10.sp, color = Color.Red)
                },
            )

            Spacer(modifier = Modifier.fillMaxHeight(.01f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state.termsAccepted,
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Primary,
                        checkedColor = Primary,
                        disabledIndeterminateColor = White
                    ),
                    onCheckedChange = {
                        signupViewModel.onEvent(SignupFormEvent.TermsChanged(it))
                    })  //TODO made check box design like
                Text(text = "Yes! Agree all", fontSize = 18.sp, color = Color.White)
                Spacer(modifier = Modifier.width(3.dp))
                ClickableText(text = AnnotatedString("Terms"),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    onClick = {})
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "&", fontSize = 18.sp, color = Color.White)
                Spacer(modifier = Modifier.width(3.dp))
                ClickableText(
                    text = AnnotatedString("Condition"),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    onClick = {},
                )
            }
            Text(text = state.termsError ?: "", fontSize = 10.sp, color = Color.Red)
            Spacer(modifier = Modifier.fillMaxHeight(.02f))
            AuthorisationButton(
                title = "Sign Up",
                textColor = Primary,
                backgroundColor = Color.White
            ) {
                signupViewModel.onEvent(SignupFormEvent.Submit)
            }
        }
    }
}

@Composable
fun SignUpBackground() {
    Image(
        painter = painterResource(id = R.drawable.splash_background),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = GenericShape { size, _ ->
                val rect = Rect(
                    left = -1.1f * size.width,
                    right = 2.1f * size.width,
                    top = size.height * 0.2f,
                    bottom = size.height / 0.26f //* .6f
                )

                addOval(rect)
                lineTo(size.width, size.height)
                close()
            }),
    )
}

@Composable
@Preview
fun SignupPreview() {
    val navController = rememberNavController()
    SignupScreen(navController)
}
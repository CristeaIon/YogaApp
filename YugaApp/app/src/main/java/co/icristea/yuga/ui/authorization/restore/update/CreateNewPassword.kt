package co.icristea.yuga.ui.authorization.restore.update

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import co.icristea.yuga.ui.theme.Grey
import co.icristea.yuga.ui.theme.HintColor

@Composable
fun CreateNewPasswordScreen(navController: NavController) {
    val updatePasswordViewModel = hiltViewModel<UpdatePasswordViewModel>()
    val state = updatePasswordViewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        updatePasswordViewModel.validationEvents.collect { result ->
            when (result) {
                is AuthorisationEvent.Success -> {
                    navController.navigate(Screen.PasswordChanged.route)
                }

                is AuthorisationEvent.Error -> {

                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        CreateNewPasswordScreenBackground()
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(120.dp)
            )
            Spacer(modifier = Modifier.height(84.dp))
            Text(
                text = "Create Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Create a new password and please never share it with anyone for safe use.",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 27.dp),
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(23.dp))
            AuthorizationTextField(
                value = state.password,
                visualTransformation = PasswordVisualTransformation(),
                onChange = {
                    updatePasswordViewModel.onEvent(UpdatePasswordFormEvent.PasswordChanged(it))
                },
                label = {
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.secure),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = HintColor, BlendMode.SrcIn)
                        )
                        Text(text = "New Password", color = Grey)
                    }
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.eyes),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = HintColor, BlendMode.SrcIn)
                    )
                },
                isError = !state.passwordError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.passwordError ?: "", fontSize = 10.sp, color = Color.Red)
                })
            Spacer(modifier = Modifier.height(23.dp))
            AuthorizationTextField(
                value = state.repeatedPassword,
                visualTransformation = PasswordVisualTransformation(),
                onChange = {
                    updatePasswordViewModel.onEvent(
                        UpdatePasswordFormEvent.RepeatedPasswordChanged(
                            it
                        )
                    )
                },
                label = {
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.secure),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = HintColor, BlendMode.SrcIn)
                        )
                        Text(text = "Confirm Password", color = Grey)
                    }
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.eyes),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = HintColor, BlendMode.SrcIn)
                    )
                },
                isError = !state.repeatedPasswordError.isNullOrEmpty(),
                errorMessage = {
                    Text(
                        text = state.repeatedPasswordError ?: "",
                        fontSize = 10.sp,
                        color = Color.Red
                    )
                })
            Spacer(modifier = Modifier.height(30.dp))
            AuthorisationButton(
                title = "Update Password",
                textColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                navController.navigate(Screen.VerificationCode.route)
            }
        }
    }
}


@Composable
fun CreateNewPasswordScreenBackground() {
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
                    top = size.height * 0.25f,
                    bottom = size.height //* .6f
                )
                lineTo(0f, size.height * .35f)
                arcTo(rect, -180f, 180.0f, false)
                lineTo(size.width, 0f)
                close()
            }),
    )
}

@Composable
@Preview(showBackground = true)
fun CreateNewPasswordScreenPreview() {
    val navController = rememberNavController()
    CreateNewPasswordScreen(navController)
}
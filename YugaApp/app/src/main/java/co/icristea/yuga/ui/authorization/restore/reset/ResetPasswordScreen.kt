package co.icristea.yuga.ui.authorization.restore.reset

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import co.icristea.yuga.ui.theme.HintColor

@Composable
fun ResetPasswordScreen(navController: NavController) {

    val restorePasswordViewModel = hiltViewModel<RestorePasswordViewModel>()
    val state = restorePasswordViewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        restorePasswordViewModel.validationEvents.collect { event ->
            when (event) {
                is AuthorisationEvent.Success -> {
                    navController.navigate(Screen.VerificationCode.route)
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
            .background(MaterialTheme.colorScheme.background),
    ) {
        ResetPasswordBackground()
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.1f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(120.dp)
            )
            Spacer(modifier = Modifier.fillMaxHeight(.15f))
            Text(
                text = "Reset Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Please enter your email address. You will get a link to create new password by email",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 27.dp),
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            AuthorizationTextField(
                value = state.email,
                onChange = {
                    restorePasswordViewModel.onEvent(RestoreFormEvent.EmailChanged(it))
                },
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(text = "your email@gmail.com", color = HintColor)
                    }
                },
                label = {
                    Text(text = "Email", color = HintColor)
                },
                isError = !state.emailError.isNullOrEmpty(),
                errorMessage = {
                    Text(text = state.emailError ?: "", fontSize = 10.sp, color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            AuthorisationButton(
                title = "Send New Password",
                textColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                restorePasswordViewModel.onEvent(RestoreFormEvent.Submit)
            }
        }
    }
}


@Composable
fun ResetPasswordBackground() {
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
                    top = size.height * 0.27f,
                    bottom = size.height //* .6f
                )
                lineTo(0f, size.height * .45f)
                arcTo(rect, -180f, 180.0f, false)
                lineTo(size.width, 0f)
                close()
            }),
    )
}

@Composable
@Preview(showBackground = true)
fun ResetPasswordScreenPreview() {
    val navController = rememberNavController()
    ResetPasswordScreen(navController)
}
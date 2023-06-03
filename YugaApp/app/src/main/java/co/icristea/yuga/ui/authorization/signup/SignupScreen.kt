package co.icristea.yuga.ui.authorization.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import co.icristea.yuga.ui.theme.Grey
import co.icristea.yuga.ui.theme.HintColor
import co.icristea.yuga.ui.theme.Primary
import co.icristea.yuga.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
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

        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            focusedLabelColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White,
            unfocusedBorderColor = Color.White,
            placeholderColor = Color.White,
            errorBorderColor = Color.Red,
        )

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

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.fullName,
                onValueChange = {
                    signupViewModel.onEvent(SignupFormEvent.FullNameChanged(it))
                },
                placeholder = {
                    Text(text = "Your name here",color = HintColor)
                },
                label = {
                    Text(text = "Full Name",color = White)
                },
                isError = !state.fullNameError.isNullOrEmpty(),
                supportingText = {
                    Text(text = state.fullNameError ?: "", fontSize = 10.sp,color = Color.Red)
                },
                leadingIcon = {

                },
                shape = RoundedCornerShape(45.dp),
                colors = textFieldColors,
            )

            Spacer(modifier = Modifier.fillMaxHeight(.01f))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.email,
                onValueChange = {
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

                        Text(text = "your email@gmail.com",color = HintColor)
                    }
                },
                label = {
                    Text(text = "Email",color = White)
                },
                leadingIcon = {
                },
                colors = textFieldColors,
                shape = RoundedCornerShape(45.dp),
                isError = !state.emailError.isNullOrEmpty(),
                supportingText = {
                    Text(text = state.emailError ?: "", fontSize = 10.sp,color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(.01f))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.phone,
                onValueChange = {
                    signupViewModel.onEvent(SignupFormEvent.PhoneChanged(it))
                },
                placeholder = {
                    Text(text = "+373 699999",color = HintColor)
                },
                label = {
                    Text(text = "Phone",color = White)
                },
                leadingIcon = {

                },
                colors = textFieldColors,
                shape = RoundedCornerShape(45.dp),
                isError = !state.phoneError.isNullOrEmpty(),
                supportingText = {
                    Text(text = state.phoneError ?: "", fontSize = 10.sp,color = Color.Red)
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(.01f))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    signupViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                },
                label = {
                    Text(text = "Password",color = White)
                },
                leadingIcon = {

                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.eyes), contentDescription = "")
                },
                colors = textFieldColors,
                shape = RoundedCornerShape(45.dp),
                isError = !state.passwordError.isNullOrEmpty(),
                supportingText = {
                    Text(text = state.passwordError ?: "", fontSize = 10.sp,color = Color.Red)
                },
            )

            Spacer(modifier = Modifier.fillMaxHeight(.01f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state.termsAccepted,
                    colors= CheckboxDefaults.colors(
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
            Text(text = state.termsError ?: "", fontSize = 10.sp,color = Color.Red)
            Spacer(modifier = Modifier.fillMaxHeight(.02f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 16.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Primary
                ),
                shape = RoundedCornerShape(45.dp),
                onClick = {
                    signupViewModel.onEvent(SignupFormEvent.Submit)
                }
            ) {
                Text(text = "Sign Up", fontSize = 20.sp)
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
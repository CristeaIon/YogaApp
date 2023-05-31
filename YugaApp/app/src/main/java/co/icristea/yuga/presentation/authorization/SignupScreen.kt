package co.icristea.yuga.presentation.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun SignupScreen(navController: NavController) {

    val signupViewModel = hiltViewModel<SignupViewModel>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(110.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(text = "Sign Up", fontSize = 36.sp, color = Color.White)
            Spacer(modifier = Modifier.height(23.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = signupViewModel.fullName.value,
                onValueChange = signupViewModel::onFullNameChanged,
                placeholder = {
                    Text(text = "Your name here")
                },
                label = {
                    Text(text = "Full Name")
                },

                shape = RoundedCornerShape(45.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(23.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = signupViewModel.email.value,
                onValueChange = signupViewModel::onEmailChanged,
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
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(23.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = signupViewModel.phone.value,
                onValueChange = signupViewModel::onPhoneChanged,
                placeholder = {
                    Text(text = "+373 699999")
                },
                label = {
                    Text(text = "Phone")
                },

                shape = RoundedCornerShape(45.dp),
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(23.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = signupViewModel.password.value,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = signupViewModel::onPasswordChanged,
                label = {
                    Text(text = "Password")
                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.eyes), contentDescription = "")
                },
                shape = RoundedCornerShape(45.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(22.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = true, onCheckedChange = {})  //TODO made check box design like
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
            Spacer(modifier = Modifier.height(35.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 16.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(45.dp),
                onClick = signupViewModel::onSubmit
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
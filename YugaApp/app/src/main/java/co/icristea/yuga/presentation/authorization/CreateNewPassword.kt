package co.icristea.yuga.presentation.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.core.navigation.Screen

@Composable
fun CreateNewPasswordScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(120.dp)
            )
            Spacer(modifier = Modifier.height(84.dp))
            Text(text = "Create Password", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Create a new password and please never share it with anyone for safe use.",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 27.dp),
                textAlign = TextAlign.Center
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
                    Text(text = "New Password")
                },
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.secure), contentDescription = "")
                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.eyes), contentDescription = "")
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
                    Text(text = "Confirm Password")
                },
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.secure), contentDescription = "")
                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.eyes), contentDescription = "")
                },
                shape = RoundedCornerShape(45.dp),
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(45.dp),
                onClick = {
                    navController.navigate(Screen.VerificationCode.route)
                }) {
                Text(text = "Update Password", fontSize = 20.sp)
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
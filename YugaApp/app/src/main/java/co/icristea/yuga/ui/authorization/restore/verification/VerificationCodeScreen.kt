package co.icristea.yuga.ui.authorization.restore.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.ui.theme.Grey
import co.icristea.yuga.ui.theme.Primary
import kotlinx.coroutines.flow.collect

@Composable
fun VerificationCodeScreen(navController: NavController) {

    val validateCodeViewModel = hiltViewModel<ValidationCodeViewModel>()
    val state = validateCodeViewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        validateCodeViewModel.validationEvents.collect { result ->
            when (result) {
                is AuthorisationEvent.Success -> {}
                is AuthorisationEvent.Error -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )

    VerificationCodeBackground()
    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Primary)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(75.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.width(120.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(86.dp))

        Text(
            text = "Verification Code",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Please type the verification code sent to ",
            fontSize = 15.sp,
            color = Color.White
        )
        ClickableText(
            text = AnnotatedString("youremail@gmail.com"),
            onClick = {},
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White)
        )
        Spacer(modifier = Modifier.height(26.dp))
        BasicTextField(
            value = state.code,
            onValueChange = {
                if (it.length <= 4) {
                    validateCodeViewModel.onEvent(ValidationFormEvent.CodeChanged(it))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(4) { index ->
                        val char = when {
                            index >= state.code.length -> ""
                            else -> state.code[index].toString()
                        }
                        Text(
                            text = char,
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .border(
                                    1.dp, Grey,
                                    RoundedCornerShape(12.dp)
                                )
                                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                                .padding(6.dp),
                            style = MaterialTheme.typography.displaySmall,
                            color = Primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "I don't receive a code!", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            ClickableText(text = AnnotatedString("Please resend"),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                onClick = {

                })
        }
    }
}

@Composable
fun VerificationCodeBackground() {
    Image(
        painter = painterResource(id = R.drawable.splash_background),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = GenericShape { size, _ ->
                val rect = Rect(
                    left = -1f * size.width,
                    right = 2f * size.width,
                    top = size.height * 0.25f,
                    bottom = size.height / 0.3f //* .6f
                )

                addOval(rect)
                lineTo(size.width, size.height)
                close()
            }),
    )

}

@Composable
@Preview
fun VerificationCodeScreenPreview() {
    val navController = rememberNavController()
    VerificationCodeScreen(navController)
}
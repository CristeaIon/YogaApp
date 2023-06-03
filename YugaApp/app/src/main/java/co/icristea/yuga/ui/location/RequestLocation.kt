package co.icristea.yuga.ui.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.icristea.yuga.R
import co.icristea.yuga.ui.theme.BlackText
import co.icristea.yuga.ui.theme.Grey

@Composable
fun RequestLocation(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(150.dp))
            Image(painter = painterResource(id = R.drawable.location), contentDescription = "")
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "HI ! Make your location allow with us",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = BlackText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Set your location to start find trainer around you",
                fontSize = 15.sp,
                color = Grey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 60.dp)
            )
            Spacer(modifier = Modifier.height(27.dp))

            Button(modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(45.dp),
                onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.arrow), contentDescription = "")
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Use location", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(27.dp))
            Text(
                text = "We only access your location while you are using this location app",
                fontSize = 15.sp,
                color = Grey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = "Or ", fontSize = 18.sp, color = MaterialTheme.colors.primary)
                Text(text = "set your location manually", fontSize = 18.sp)
            }
        }
    }
}

@Composable
@Preview
fun RequestLocationPreview() {
    val navController = rememberNavController()
    RequestLocation(navController)
}
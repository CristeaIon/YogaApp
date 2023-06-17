package co.icristea.yuga.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthorisationButton(
    title: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(horizontal = 16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(45.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        onClick = onClick
    ) {
        Text(text = title, fontSize = 20.sp, color = textColor)
    }
}

@Composable
@Preview(showBackground = true)
fun AuthorizationButtonPreview() {
    AuthorisationButton(
        title = "test",
        textColor = Color.Red,
        backgroundColor = Color.Blue,
        onClick = {})
}
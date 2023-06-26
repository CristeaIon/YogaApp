package co.icristea.yuga.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icristea.yuga.ui.theme.TextFieldBorderColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationTextField(
    value: String,
    textColor: Color? = Color.Black,
    label: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onChange: (String) -> Unit,
    isError: Boolean,
    placeholder: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    errorMessage: @Composable() (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        visualTransformation = visualTransformation,
        onValueChange = onChange,
        label = label,
        leadingIcon = {},
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(45.dp),
        isError = isError,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textColor!!,
            focusedLabelColor = TextFieldBorderColor,
            focusedBorderColor = TextFieldBorderColor,
            unfocusedLabelColor = TextFieldBorderColor,
            unfocusedBorderColor = TextFieldBorderColor,
            placeholderColor = TextFieldBorderColor,
            errorBorderColor = Color.Red,
        ),
        supportingText = if (isError) errorMessage else null
    )
}

@Composable
@Preview(showBackground = true)
fun AuthTExtFieldPreview() {
    AuthorizationTextField(
        value = "test",
        label = { Text("error") },
        onChange = {},
        isError = false,
        errorMessage = { Text("error") },
    )
}
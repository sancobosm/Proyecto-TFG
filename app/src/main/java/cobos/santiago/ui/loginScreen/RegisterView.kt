package cobos.santiago

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MyRegisterView() {
    MyBody()
}

@Composable
fun MyBody() {
    var emailText by remember { mutableStateOf(("")) }
    var nameText by remember { mutableStateOf(("")) }
    var lastNameText by remember { mutableStateOf(("")) }
    var passwordText by remember { mutableStateOf(("")) }
    var confirmPasswordText by remember { mutableStateOf(("")) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {

        MyField(text = emailText, {
            emailText = it
        }, "Email")
        Spacer(Modifier.size(10.dp))
        MyField(text = nameText, {
            nameText = it
        }, "Name")
        Spacer(Modifier.size(10.dp))
        MyField(text = lastNameText, {
            lastNameText = it
        }, "Last name")
        Spacer(Modifier.size(10.dp))
        MyFieldPassword(text = passwordText, {
            passwordText = it
        }, "Password")
        Spacer(Modifier.size(10.dp))
        MyFieldPassword(text = confirmPasswordText, {
            confirmPasswordText = it
        }, "Confirm password")
        Spacer(Modifier.size(50.dp))
        Button(
            modifier = Modifier
                .width(300.dp),
            onClick = { /*TODO*/ }) {
            Text("Confirm Registration")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyField(text: String, onValueChanged: (String) -> Unit, s: String) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = text,
        onValueChange = onValueChanged,
        singleLine = true,
        placeholder = {
            Text(text = s, color = Color(0xFFB5B5B5))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFieldPassword(text: String, onValueChanged: (String) -> Unit, s: String) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = text,
        onValueChange = onValueChanged,
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                val image = if (passwordVisibility) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                Icon(imageVector = image, contentDescription = null)
            }
        },
        placeholder = {
            Text(text = s, color = Color(0xFFB5B5B5))
        }
    )

}


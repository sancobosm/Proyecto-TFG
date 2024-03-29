package cobos.santiago

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cobos.santiago.data.remote.Auth
import cobos.santiago.ui.viewmodels.RegisterViewModel
import cobos.santiago.ui.viewmodels.UserViewModel

@Composable
fun MyRegisterView(navController: NavController) {
    MyBody(navController)
}

@Composable
fun MyBody(navController: NavController) {

    val viewModel = hiltViewModel<RegisterViewModel>()
    val userViewModel = hiltViewModel<UserViewModel>()


    var nameText by remember { mutableStateOf(("")) }
    var lastNameText by remember { mutableStateOf(("")) }

    val emailText: String by viewModel.email.observeAsState(initial = "")
    val passwordText: String by viewModel.password.observeAsState(initial = "")
    val confirmPasswordText: String by viewModel.confirm_password.observeAsState(initial = "")
    val isButtonEnabled: Boolean by viewModel.isButtonEnabled.observeAsState(initial = false)
    val isRegisterError: Boolean by viewModel.isRegisterSuccess.observeAsState(initial = false)


    //for register
    val auth = Auth(userViewModel)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        MyField(text = emailText, {
            viewModel.onTextChanged(it)
            viewModel.onRegisterChanged(it, passwordText, confirmPasswordText)
        }, "Email", isRegisterError)
        Spacer(Modifier.size(10.dp))

        MyField(text = nameText, {
            nameText = it
        }, "Name", isRegisterError)
        Spacer(Modifier.size(10.dp))

        MyField(text = lastNameText, {
            lastNameText = it
        }, "Last name", isRegisterError)
        Spacer(Modifier.size(10.dp))

        MyFieldPassword(text = passwordText, {
            viewModel.onPasswordChanged(it)
            viewModel.onRegisterChanged(emailText, it, confirmPasswordText)
        }, "Password", isRegisterError)
        Spacer(Modifier.size(10.dp))

        MyFieldPassword(text = confirmPasswordText, {
            viewModel.onConfirmPasswordChanged(it)
            viewModel.onRegisterChanged(emailText, passwordText, it)
        }, "Confirm password", isRegisterError)
        Spacer(Modifier.size(50.dp))

        Button(
            modifier = Modifier
                .width(300.dp),
            enabled = isButtonEnabled,
            onClick = {
                auth.createUser(emailText, passwordText)
                Thread.sleep(1000)
                auth.makeLogin(emailText, passwordText, navController) {
                    //caso de error
                    viewModel.onRegisterError(true)
                    viewModel.onRegisterChanged("", "", "")
                }
            }) {
            Text("Confirm Registration")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyField(
    text: String,
    onValueChanged: (String) -> Unit,
    s: String,
    errorSate: Boolean
) {
    TextField(
        value = text,
        onValueChange = onValueChanged,
        isError = errorSate,
        singleLine = true,
        placeholder = {
            Text(text = s, color = Color(0xFFB5B5B5))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFieldPassword(
    text: String,
    onValueChanged: (String) -> Unit,
    s: String,
    errorState: Boolean
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = text,
        onValueChange = onValueChanged,
        singleLine = true,
        isError = errorState,
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


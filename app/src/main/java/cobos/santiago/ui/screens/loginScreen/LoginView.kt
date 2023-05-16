package cobos.santiago.ui.screens.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cobos.santiago.MyField
import cobos.santiago.MyFieldPassword
import cobos.santiago.MyForgotPassword
import cobos.santiago.R
import cobos.santiago.data.remote.Auth
import cobos.santiago.ui.viewmodels.LoginViewModel

@Composable
fun MyLoginView(navController: NavController) {
    //viewmodel
    val viewModel = hiltViewModel<LoginViewModel>()

    //live data logic
    val loginText: String by viewModel.email.observeAsState(initial = "")
    val passwordText: String by viewModel.password.observeAsState(initial = "")
    val isButtonEnabled: Boolean by viewModel.isButtonEnabled.observeAsState(initial = false)

    val isLoginError: Boolean by viewModel.isLoginSuccess.observeAsState(initial = false)

    val auth = Auth()
    //column for main content of the login
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        MyField(text = loginText,
            {
                viewModel.onTextChanged(it)
                viewModel.onLoginChanged(it, passwordText)
            }, "Email", isLoginError
        )
        Spacer(modifier = Modifier.size(20.dp))
        MyFieldPassword(text = passwordText, {
            viewModel.onPasswordChanged(it)
            viewModel.onLoginChanged(loginText, it)
        }, "Password", isLoginError)
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            modifier = Modifier
                .width(200.dp),
            enabled = isButtonEnabled,
            onClick = {
                //navController.navigate(AppScreens.HomeScreen.ruta)
                auth.makeLogin(loginText, passwordText, navController) {
                    //caso de error
                    viewModel.onLoginError(true)
                    viewModel.onLoginChanged("", "")
                }
            }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.size(20.dp))
        MyForgotPassword()
        Spacer(modifier = Modifier.size(30.dp))
        MySocialMediaButtom(R.drawable.google_vector, "google") {

        }
        Spacer(modifier = Modifier.size(10.dp))
        MySocialMediaButtom(R.drawable.facebook, "facebook", {})
    }
}

@Composable
fun MySocialMediaButtom(drawable: Int, s: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .height(50.dp)
            .width(2000.dp),
        onClick = { onClick }) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(drawable),
                contentDescription = "",
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.size(40.dp))
            Text("Login via $s")
        }
    }
}
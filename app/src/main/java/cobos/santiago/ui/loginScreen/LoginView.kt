package cobos.santiago.ui.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cobos.santiago.MyField
import cobos.santiago.MyFieldPassword
import cobos.santiago.MyForgotPassword
import cobos.santiago.R
import cobos.santiago.navigation.AppScreens

@Composable
fun MyLoginView(navController: NavController) {
    var loginText by remember { mutableStateOf(("")) }
    var passwordText by remember { mutableStateOf(("")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        MyField(text = loginText, {
            loginText = it
        }, "Email")
        Spacer(modifier = Modifier.size(20.dp))
        MyFieldPassword(text = passwordText, {
            passwordText = it
        }, "Password")
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            modifier = Modifier
                .width(200.dp),
            onClick = {
                navController.navigate(AppScreens.HomeScreen.ruta)
            }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.size(20.dp))
        MyForgotPassword()
        Spacer(modifier = Modifier.size(30.dp))
        MySocialMediaButtom(R.drawable.google_vector, "google")
        Spacer(modifier = Modifier.size(10.dp))
        MySocialMediaButtom(R.drawable.facebook, "facebook")

    }
}

@Composable
fun MySocialMediaButtom(drawable: Int, s: String) {
    OutlinedButton(
        modifier = Modifier
            .height(50.dp)
            .width(2000.dp),
        onClick = { /*TODO*/ }) {
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
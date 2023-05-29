package cobos.santiago.ui.screens.mainScreen.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cobos.santiago.R
import cobos.santiago.data.entities.User
import cobos.santiago.data.remote.Auth
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.viewmodels.UserViewModel
import com.airbnb.lottie.compose.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyProfile(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        MyProfileAnimation()
    }
    MyBodyProfile(navController = navController)
}

@Composable
fun MyProfileAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.profile))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBodyProfile(navController: NavController) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val user: User = userViewModel.getCurrentUser().value!!
    val auth = Auth(userViewModel)


    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 190.dp, start = 10.dp, end = 10.dp, bottom = 50.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = user.image),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))
            val textFieldColors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint, // Color de la línea cuando el TextField está enfocado
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint, // Color de la línea cuando el TextField no está enfocado
                errorIndicatorColor = MaterialTheme.colorScheme.surfaceTint // Color de la línea cuando el TextField está en estado de error
            )
            TextField(
                value = user.email,
                onValueChange = {},
                enabled = true,
                readOnly = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Email:",
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                },
                shape = TextFieldDefaults.filledShape,
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = user.firstName,
                onValueChange = {},
                enabled = true,
                readOnly = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "First Name",
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                },
                shape = TextFieldDefaults.filledShape,
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = user.secondName,
                onValueChange = {},
                enabled = true,
                readOnly = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Second Name",
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                },
                shape = TextFieldDefaults.filledShape,
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = "***************",
                onValueChange = {},
                enabled = true,
                readOnly = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Password:",
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                },
                shape = TextFieldDefaults.filledShape,
                colors = textFieldColors
            )
            Spacer(Modifier.size(16.dp))
            OutlinedButton(
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp),
                onClick = {
                    showDialog.value = true
                }
            ) {
                Text(text = "Logout")
            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Logout") },
                    text = { Text("Are you sure you want to logout?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // Realizar acciones de confirmación
                                showDialog.value = false
                                Log.i("userr", "${auth.isUserLoggedIn()} antes")
                                FirebaseAuth.getInstance().signOut()
                                Log.i("userr", "${auth.isUserLoggedIn()} despues")
                                navController.navigate(AppScreens.LoginScreen.route)

                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

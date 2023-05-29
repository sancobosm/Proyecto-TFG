package cobos.santiago.ui.screens.mainScreen.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cobos.santiago.R
import cobos.santiago.data.entities.User
import cobos.santiago.data.remote.Auth
import cobos.santiago.ui.viewmodels.UserViewModel
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*


@Composable
fun MySettings() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        MySettingsAnimation()
    }
    MySettingsBody()
}

@Composable
fun MySettingsAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.settings))
    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = LottieConstants.IterateForever
    )
    LottieAnimation(composition = composition, progress = { progress })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySettingsBody() {
    val userViewModel = hiltViewModel<UserViewModel>()
    val user: User = userViewModel.getCurrentUser().value!!
    val auth = Auth(userViewModel)


    val emailEditMode = remember { mutableStateOf(false) }
    val firstNameEditMode = remember { mutableStateOf(false) }
    val secondNameEditMode = remember { mutableStateOf(false) }
    val passwordEditMode = remember { mutableStateOf(false) }

    val emailValue = remember { mutableStateOf(user.email) }
    val firstNameValue = remember { mutableStateOf(user.firstName) }
    val secondNameValue = remember { mutableStateOf(user.secondName) }

    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 180.dp, start = 10.dp, end = 10.dp, bottom = 50.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture()

            Spacer(modifier = Modifier.height(4.dp))
            /* val textFieldColors = TextFieldDefaults.textFieldColors(
                 focusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint, // Color de la línea cuando el TextField está enfocado
                 unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint, // Color de la línea cuando el TextField no está enfocado
                 errorIndicatorColor = MaterialTheme.colorScheme.surfaceTint // Color de la línea cuando el TextField está en estado de error
             )*/
            TextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it },
                readOnly = !emailEditMode.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Email:"
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = { emailEditMode.value = !emailEditMode.value },
                        enabled = !emailEditMode.value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (emailEditMode.value) {
                        IconButton(
                            onClick = { emailEditMode.value = !emailEditMode.value },
                            //    enabled = !emailEditMode.value
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check, contentDescription = null
                            )
                        }
                    }
                },
                shape = TextFieldDefaults.filledShape,
            )

            Spacer(modifier = Modifier.height(7.dp))

            TextField(
                value = firstNameValue.value,
                onValueChange = { firstNameValue.value = it },
                readOnly = !firstNameEditMode.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "First name:"
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = { firstNameEditMode.value = !firstNameEditMode.value },
                        enabled = !firstNameEditMode.value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (firstNameEditMode.value) {
                        IconButton(
                            onClick = { firstNameEditMode.value = !firstNameEditMode.value },
                            //    enabled = !firstNameEditMode.value
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check, contentDescription = null
                            )
                        }
                    }
                },
                shape = TextFieldDefaults.filledShape,
            )

            Spacer(modifier = Modifier.height(7.dp))

            TextField(
                value = secondNameValue.value,
                onValueChange = { secondNameValue.value = it },
                readOnly = !secondNameEditMode.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Second name:"
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = { secondNameEditMode.value = !secondNameEditMode.value },
                        enabled = !secondNameEditMode.value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (secondNameEditMode.value) {
                        IconButton(
                            onClick = { secondNameEditMode.value = !secondNameEditMode.value },
                            //    enabled = !secondNameEditMode.value
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check, contentDescription = null
                            )
                        }
                    }
                },
                shape = TextFieldDefaults.filledShape,
            )
            Spacer(modifier = Modifier.height(7.dp))

            TextField(
                value = "***************",
                onValueChange = {},
                readOnly = !passwordEditMode.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Password:"
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = { passwordEditMode.value = !passwordEditMode.value },
                        enabled = !passwordEditMode.value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (passwordEditMode.value) {
                        IconButton(
                            onClick = { passwordEditMode.value = !passwordEditMode.value },
                            //    enabled = !passwordEditMode.value
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check, contentDescription = null
                            )
                        }
                    }
                },
                shape = TextFieldDefaults.filledShape,
            )
            Spacer(Modifier.size(7.dp))
            OutlinedButton(
                modifier = Modifier
                    .height(40.dp)
                    .width(160.dp),
                onClick = {
                    showDialog.value = true
                }
            ) {
                Text(text = "Confirm edition")
            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Confirm Edition") },
                    text = { Text("Are you sure you want to save the changes?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false


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

@Composable
fun ProfilePicture() {
    val userViewModel = hiltViewModel<UserViewModel>()
    // var selectedImageUri by remember { mutableStateOf("") }
    var selectedImageUri = userViewModel.selectedImageUri.value
    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            // Lógica para establecer la imagen seleccionada como la imagen de perfil
            selectedImageUri = uri.toString()
            userViewModel.saveProfileImage(uri.toString())
        }
    Box(modifier = Modifier.padding(0.dp)) {
        val painter: Painter = if (selectedImageUri.equals("")) {
            rememberImagePainter(selectedImageUri)
        } else {
            painterResource(id = R.drawable.profile_image)
        }

        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.surfaceTint, CircleShape)
        ) {
            Image(
                painter = painter,
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
    TextButton(
        onClick = { imageLauncher.launch("image/*") },
    ) {
        Text(text = "Edit your profile image")
    }
}




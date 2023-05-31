package cobos.santiago.ui.screens.mainScreen.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cobos.santiago.R
import cobos.santiago.data.entities.User
import cobos.santiago.data.remote.Auth
import cobos.santiago.ui.viewmodels.UserViewModel
import com.airbnb.lottie.compose.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream


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

    val viewModel = hiltViewModel<UserViewModel>()
    val showDialog = remember { mutableStateOf(false) }
    val selectedImageUri = viewModel.selectedImageUri.value

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
            TextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it },
                readOnly = !emailEditMode.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 9.dp),
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
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Confirm Edition") },
                    text = { Text("Are you sure you want to save the changes?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false

                                selectedImageUri?.let {
                                    uploadImageToFirebase(it)
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Edition confirmed")
                                    }
                                }
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Canceled")
                                }
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
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val viewModel = hiltViewModel<UserViewModel>()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Descargar y almacenar la imagen localmente
            GlobalScope.launch(Dispatchers.IO) {
                uri?.let {
                    val inputStream = context.contentResolver.openInputStream(it)
                    inputStream?.use { input ->
                        val file = File(context.cacheDir, "profile_image.jpg")
                        val outputStream = FileOutputStream(file)
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                        val savedUri = Uri.fromFile(file)

                        // Actualizar el estado de la imagen seleccionada
                        viewModel.setSelectedImageUri(savedUri)

                        // Subir la imagen a Firebase Storage solo cuando se confirman los cambios
                    }
                }
            }
        }

    val selectedImageUri = viewModel.selectedImageUri.value

    LaunchedEffect(selectedImageUri) {
        selectedImageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
        }
    }

    // Cargar la imagen de Firebase si está disponible, de lo contrario cargar la imagen predeterminada
    LaunchedEffect(Unit) {
        val firebaseImageUri = downloadImageFromFirebase()
        if (firebaseImageUri != null) {
            bitmap.value = loadBitmapFromUri(context, firebaseImageUri)
        } else {
            bitmap.value = BitmapFactory.decodeResource(context.resources, R.drawable.profile_image)
        }
    }

    bitmap.value?.let { btm ->
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.surfaceTint, CircleShape)
        ) {
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
    TextButton(
        onClick = { launcher.launch("image/*") },
    ) {
        Text(text = "Edit your profile image")
    }

}

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}

suspend fun downloadImageFromFirebase(): Uri? {
    return try {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("profile_images/profile_image.jpg")
        val file = File.createTempFile("profile_image", "jpg")
        storageRef.getFile(file).await()
        Uri.fromFile(file)
    } catch (e: Exception) {
        null
    }
}


fun uploadImageToFirebase(imageUri: Uri) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference.child("profile_images").child(imageUri.lastPathSegment!!)
    val uploadTask = storageRef.putFile(imageUri)

    uploadTask.addOnSuccessListener {
    }.addOnFailureListener { exception ->
    }
}

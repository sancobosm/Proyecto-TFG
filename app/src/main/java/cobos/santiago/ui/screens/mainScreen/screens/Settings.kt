package cobos.santiago.ui.screens.mainScreen.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cobos.santiago.R
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySettings(navController: NavController) {
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            ProfilePicture()

            OutlinedTextField(
                value = "Nombre de usuario",
                onValueChange = { /* Cambiar el nombre de usuario */ },
                modifier = Modifier.padding(top = 16.dp)
            )
            TextField(
                value = "Correo electrónico",
                onValueChange = { /* Cambiar el correo electrónico */ },
                modifier = Modifier.padding(top = 16.dp)
            )
            TextField(
                value = "Contraseña",
                onValueChange = { /* Cambiar la contraseña */ },
                modifier = Modifier.padding(top = 16.dp)
            )

            var showDialog by remember { mutableStateOf(false) }

            Button(
                onClick = { showDialog = !showDialog }, modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Mostrar ventana de confirmación")
            }

            if (showDialog) {
                FloatingConfirmWindow(confirmText = "Confirmar",
                    cancelText = "Cancelar",
                    onConfirm = { /* Lógica al confirmar */ },
                    onCancel = { /* Lógica al cancelar */ })
            }
        }
    }
}

@Composable
fun ProfilePicture() {
    var selectedImageUri by remember { mutableStateOf("") }
    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            // Lógica para establecer la imagen seleccionada como la imagen de perfil
            selectedImageUri = uri.toString()
        }
    Box(modifier = Modifier.padding(0.dp)) {
        val painter: Painter = if (selectedImageUri.isNotEmpty()) {
            rememberImagePainter(selectedImageUri)
        } else {
            painterResource(id = R.drawable.profile_image)
        }

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
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

@Composable
fun FloatingConfirmWindow(
    confirmText: String, cancelText: String, onConfirm: () -> Unit, onCancel: () -> Unit
) {
    val dialogShown = remember { mutableStateOf(true) }

    if (dialogShown.value) {
        AlertDialog(onDismissRequest = { dialogShown.value = false }, title = {
            Text(text = "Confirmación")
        }, text = {
            Text(text = "¿Estás seguro de realizar esta acción?")
        }, confirmButton = {
            Button(onClick = {
                dialogShown.value = false
                onConfirm()
            }) {
                Text(text = confirmText)
            }
        }, dismissButton = {
            Button(onClick = {
                dialogShown.value = false
                onCancel()
            }) {
                Text(text = cancelText)
            }
        })
    }
}

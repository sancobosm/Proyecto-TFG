package cobos.santiago.ui.screens.mainScreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.viewmodels.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfile(navController: NavController) {
    val viewModel: ProfileViewModel = hiltViewModel()

    val newName: String by viewModel.newName.observeAsState(initial = "")
    val newPassword: String by viewModel.newPassword.observeAsState(initial = "")
    val confirmPassword: String by viewModel.confirmPassword.observeAsState(initial = "")
    val errorMessage: String by viewModel.errorMessage.observeAsState(initial = "")
    val isUpdateSuccess: Boolean by viewModel.isUpdateSuccess.observeAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = newName,
            onValueChange = { viewModel.setName(it) },
            label = { Text(text = "name") },
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = newPassword,
            onValueChange = { viewModel.setPassword(it) },
            label = { Text(text = "new password") },
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.setConfirmPassword(it) },
            label = { Text(text = "confirm password") },
            modifier = Modifier.padding(16.dp)
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        if (isUpdateSuccess) {
            Text(
                text = "Profile updated",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        }
        Button(
            onClick = {
                viewModel.updateProfile()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "save")
        }
        Button(
            onClick = {
                viewModel.logout()
                navController.navigate(AppScreens.LoginScreen.route)
                // Aquí puedes agregar cualquier otra lógica necesaria al cerrar sesión, como redireccionar al usuario a la pantalla de inicio de sesión.
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Log out")
        }
    }
}

package cobos.santiago.presentation.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import cobos.santiago.components.ProgressBar
import cobos.santiago.presentation.profile.ProfileViewModel
import cobos.santiago.domain.model.Response.Failure
import cobos.santiago.domain.model.Response.Success
import cobos.santiago.domain.model.Response.Loading

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: (accessRevoked: Boolean) -> Unit,
    showSnackBar: () -> Unit
) {
    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Loading -> ProgressBar()
        is Success -> revokeAccessResponse.data?.let { accessRevoked ->
            LaunchedEffect(accessRevoked) {
                navigateToAuthScreen(accessRevoked)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(revokeAccessResponse.e)
            showSnackBar()
        }
    }
}
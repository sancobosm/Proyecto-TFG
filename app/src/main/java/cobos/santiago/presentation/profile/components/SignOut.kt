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
fun SignOut(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: (signedOut: Boolean) -> Unit
) {
    when(val signOutResponse = viewModel.signOutResponse) {
        is Loading -> ProgressBar()
        is Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                navigateToAuthScreen(signedOut)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(signOutResponse.e)
        }
    }
}
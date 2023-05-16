package cobos.santiago.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import cobos.santiago.navigation.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomBarViewModel @Inject constructor(
) : ViewModel() {
    var navController: NavHostController? = null

    fun navigateTo(destination: AppScreens) {
        navController?.navigate(destination.route)
    }
}

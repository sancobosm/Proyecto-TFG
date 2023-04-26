package cobos.santiago.navigation

import cobos.santiago.core.Constants.AUTH_SCREEN
import cobos.santiago.core.Constants.PROFILE_SCREEN


sealed class Screen(val route: String) {
    object AuthScreen: Screen(AUTH_SCREEN)
    object ProfileScreen: Screen(PROFILE_SCREEN)
}
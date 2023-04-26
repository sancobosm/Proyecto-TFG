package cobos.santiago.navigation

import cobos.santiago.core.Constants.AUTH_SCREEN
import cobos.santiago.core.Constants.PROFILE_SCREEN

sealed class AppScreens(val ruta:String) {
    object SplashScreen: AppScreens("splash_screen")
    object MainActivity: AppScreens("main_activity")
    object HomeScreen: AppScreens("home_screen")



}
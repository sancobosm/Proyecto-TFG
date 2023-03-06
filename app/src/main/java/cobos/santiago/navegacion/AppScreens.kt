package cobos.santiago.navegacion

sealed class AppScreens(val ruta:String) {
    object SplashScreen: AppScreens("splash_screen")
    object MainActivity: AppScreens("main_activity")
}
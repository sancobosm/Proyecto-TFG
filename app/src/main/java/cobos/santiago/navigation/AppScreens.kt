package cobos.santiago.navigation

sealed class AppScreens(val rute: String) {
    object SplashScreen : AppScreens("splash_screen")
    object MainActivity : AppScreens("main_activity")
    object HomeScreen : AppScreens("home_screen")

}
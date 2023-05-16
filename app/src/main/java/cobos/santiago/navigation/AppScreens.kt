package cobos.santiago.navigation


sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object MainActivity : AppScreens("main_activity")
    object HomeScreen : AppScreens("home_screen")
    object Options : AppScreens("options")
    object Profile : AppScreens("profile")

}
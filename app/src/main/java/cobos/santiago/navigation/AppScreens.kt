package cobos.santiago.navigation


sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object LoginScreen : AppScreens("login_screen")
    object HomeScreen : AppScreens("home_screen")
    object MyScaffold : AppScreens("scaffold")
    object Settings : AppScreens("settings")
    object Profile : AppScreens("profile")
    object SecondaryScreen : AppScreens("secondaryScreen")


}
package cobos.santiago.navigation


sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object FirstScreen : AppScreens("main_activity")
    object HomeScreen : AppScreens("home_screen")
    object MyScaffold : AppScreens("scaffold")
    object Settings : AppScreens("settings")
    object Profile : AppScreens("profile")

}
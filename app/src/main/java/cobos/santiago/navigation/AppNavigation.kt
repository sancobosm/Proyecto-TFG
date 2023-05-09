package cobos.santiago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cobos.santiago.MyFirstScreen
import cobos.santiago.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.rute
    ) {
        composable(AppScreens.SplashScreen.rute) {
            SplashScreen(navController)
        }
        composable(AppScreens.MainActivity.rute) {
            MyFirstScreen(navController)
        }
        composable(AppScreens.HomeScreen.rute) {
            // HomeScreen()
        }
    }
}
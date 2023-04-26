package cobos.santiago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cobos.santiago.MyFirstScreen
import cobos.santiago.SplashScreen
import cobos.santiago.ui.mainScreen.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.ruta
    ) {
        composable(AppScreens.SplashScreen.ruta) {
            SplashScreen(navController)
        }
        composable(AppScreens.MainActivity.ruta) {
            MyFirstScreen(navController)
        }
        composable(AppScreens.HomeScreen.ruta) {
            HomeScreen()
        }
    }
}
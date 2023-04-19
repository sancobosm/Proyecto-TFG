package cobos.santiago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cobos.santiago.MyFirstScreen
import cobos.santiago.SplashScreen
import cobos.santiago.ui.mainScreen.HomeScreen
import cobos.santiago.ui.viewmodels.LoginViewModel

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.ruta
    ){
        composable(AppScreens.SplashScreen.ruta){
            SplashScreen(navController)
        }
        composable(AppScreens.MainActivity.ruta){
            MyFirstScreen(navController, LoginViewModel())
        }
        composable(AppScreens.HomeScreen.ruta){
            HomeScreen()
        }
    }
}
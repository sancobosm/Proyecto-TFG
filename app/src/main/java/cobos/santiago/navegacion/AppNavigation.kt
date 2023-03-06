package cobos.santiago.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cobos.santiago.Greeting
import cobos.santiago.SplashScreen

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
            Greeting(name = "hola")
        }
    }
}
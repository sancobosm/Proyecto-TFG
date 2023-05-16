package cobos.santiago

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.OptionsScreen
import cobos.santiago.ui.screens.mainScreen.HomeScreen
import cobos.santiago.ui.theme.MusikTheme
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import com.cursokotlin.music_service.service.SimpleMediaService
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SimpleMediaViewModel by viewModels();
    private var isServiceRunning = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = AppScreens.SplashScreen.route
                    ) {
                        composable(AppScreens.SplashScreen.route) {
                            SplashScreen(navController)
                        }
                        composable(AppScreens.MainActivity.route) {
                            MyFirstScreen(navController)
                        }
                        composable(AppScreens.HomeScreen.route) {
                            HomeScreen(
                                vm = viewModel, startService = ::startService
                            )
                        }
                        composable(AppScreens.Options.route) {
                            OptionsScreen()
                        }
                        // composable(AppScreens.Search.route) { SearchScreen() }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SimpleMediaService::class.java))
        isServiceRunning = false
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, SimpleMediaService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }
}



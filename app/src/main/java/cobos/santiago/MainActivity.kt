package cobos.santiago

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.screens.mainScreen.HomeScreen
import cobos.santiago.ui.screens.mainScreen.screens.MyProfile
import cobos.santiago.ui.screens.mainScreen.screens.MySettings
import cobos.santiago.ui.theme.MusikTheme
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import com.cursokotlin.music_service.service.SimpleMediaService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SimpleMediaViewModel by viewModels()
    private var isServiceRunning = false
    private var selectedIndex by mutableStateOf(1) // Variable de estado global
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusikTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    MyNavigationHost(navController)
                }
            }
        }
    }

    @Composable
    private fun MyNavigationHost(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.SplashScreen.route
        ) {
            composable(AppScreens.SplashScreen.route) {
                SplashScreen(navController = navController)
            }
            composable(AppScreens.LoginScreen.route) {
                MyFirstScreen(navController = navController)
            }
            composable(AppScreens.MyScaffold.route) {
                MyScaffold(navController) {
                    HomeScreen(
                        vm = viewModel,
                        startService = ::startService
                    )
                }
            }
            composable(AppScreens.HomeScreen.route) {
                MyScaffold(navController) {
                    HomeScreen(
                        vm = viewModel,
                        startService = ::startService
                    )
                }
            }
            composable(AppScreens.Profile.route) {
                MyScaffold(navController) {
                    MyProfile()
                }
            }
            composable(AppScreens.Settings.route) {
                MyScaffold(navController) {
                    MySettings()
                }
            }
        }
    }

    enum class NavigationBarItems(val icon: ImageVector) {
        Person(Icons.Default.Person),
        Home(Icons.Default.Home),
        Settings(Icons.Default.Settings)
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MyScaffold(
        navController: NavHostController,
        content: @Composable () -> Unit,
    ) {
        val navigationBarItems = remember { NavigationBarItems.values() }

        Scaffold(
            modifier = Modifier.padding(all = 0.dp),
            bottomBar = {
                NavigationBar(
                    Modifier.height(77.dp)
                ) {
                    navigationBarItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navigateToDestination(navController, index)
                            },
                            label = {
                                Text(text = item.name)
                            },
                            icon = {
                                Icon(imageVector = item.icon, contentDescription = null)
                            }
                        )
                    }
                }
            }
        ) {
            content.invoke()
        }
    }


    private fun navigateToDestination(navController: NavHostController, index: Int) {
        val destination = when (index) {
            0 -> AppScreens.Profile.route
            1 -> AppScreens.HomeScreen.route
            2 -> AppScreens.Settings.route
            else -> AppScreens.HomeScreen.route
        }
        navController.navigate(destination)
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

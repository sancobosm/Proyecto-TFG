package cobos.santiago

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import cobos.santiago.navigation.NavGraph
import cobos.santiago.navigation.Screen
import cobos.santiago.presentation.auth.AuthViewModel
import com.example.compose.MusikTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel>()
    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //navController = rememberNavController()
            MusikTheme {
                navController = rememberAnimatedNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                   //AppNavigation(navController)
                    NavGraph(
                        navController = navController
                    )
                    checkAuthState()
                }
            }
        }
    }

    private fun checkAuthState() {
        if(viewModel.isUserAuthenticated) {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() = navController.navigate(Screen.ProfileScreen.route)

}



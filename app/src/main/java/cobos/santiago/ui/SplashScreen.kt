package cobos.santiago

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cobos.santiago.data.remote.Auth
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.theme.md_theme_dark_onPrimary
import cobos.santiago.ui.viewmodels.UserViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val auth = Auth(userViewModel)
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        Log.i("userr", "${auth.isUserLoggedIn()} logeado")
        if (auth.isUserLoggedIn()) {
            navController.navigate(AppScreens.MyScaffold.route)
        } else {
            Log.i("userr", "${auth.isUserLoggedIn()} logeado")
            navController.navigate(AppScreens.LoginScreen.route)
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false

    /*val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0f, 0f, 0f, 0.8f),
            darkIcons = useDarkIcons
        )
    }*/
    Splash(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(180018))
            .background(md_theme_dark_onPrimary)

    )

}

@Composable
fun Splash(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        lottieAnimacion()

    }
}


@Composable
fun lottieAnimacion() {
    val composicion by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loader))
    LottieAnimation(
        composition = composicion
    )

}

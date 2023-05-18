package cobos.santiago

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import cobos.santiago.data.remote.Auth
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.theme.md_theme_dark_onPrimary
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val auth = Auth()
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        if (auth.isUserLoggedIn()) {
            navController.navigate(AppScreens.MyScaffold.route)
        } else {
            navController.navigate(AppScreens.FirstScreen.route)
        }
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0f, 0f, 0f, 0.8f),
            darkIcons = useDarkIcons
        )
    }
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

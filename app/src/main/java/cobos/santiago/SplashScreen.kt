package cobos.santiago

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import cobos.santiago.navegacion.AppScreens
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import android.app.Activity
import android.util.Patterns
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true){
        delay(2000)
        navController.popBackStack()
        navController.navigate(AppScreens.MainActivity.ruta)
    }


    Splash(modifier = Modifier
        .fillMaxSize()
        .background(Color(180018))

    )

}

@Composable
fun Splash(modifier: Modifier) {
    Box(modifier = modifier
    ) {
        lottieAnimacion()

        Text("Holaasdfadsfasdf")
    }
}



@Composable
fun lottieAnimacion(){
    val composicion by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loader))
    LottieAnimation(
        composition = composicion
    )

}

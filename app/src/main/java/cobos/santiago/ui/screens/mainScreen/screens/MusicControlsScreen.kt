package cobos.santiago.ui.screens.mainScreen.screens

//noinspection SuspiciousImport
import android.R
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.screens.componentes.PlayerBar
import cobos.santiago.ui.screens.componentes.PlayerControls
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import cobos.santiago.ui.viewmodels.UIEvent
import com.airbnb.lottie.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryScreen(
    vm: SimpleMediaViewModel,
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                modifier = Modifier
                    .background(Color.Transparent), // Establecer el fondo transparente
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(AppScreens.HomeScreen.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Icono de flecha hacia atrás
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomPlayerUI(
                durationString = vm.formatDuration(vm.duration),
                playResourceProvider = {
                    if (vm.isPlaying) R.drawable.ic_media_pause
                    else R.drawable.ic_media_play
                },
                progressProvider = { Pair(vm.progress, vm.progressString) },
                onUiEvent = vm::onUIEvent
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            VinylAnimation(
                isSongPlaying = vm.isPlaying
            )
        }
    }
}

@Composable
fun BottomPlayerUI(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEvent) -> Unit
) {
    val (progress, progressString) = progressProvider()

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        PlayerBar(
            progress = progress,
            durationString = durationString,
            progressString = progressString,
            onUiEvent = onUiEvent
        )
        PlayerControls(
            playResourceProvider = playResourceProvider,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun Vinyl(
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f,
) {
    Box(
        modifier = modifier
            .aspectRatio(1.0f)
            .clip(MaterialTheme.shapes.medium)
    ) {
        // Vinyl background
        Image(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationDegrees),
            painter = painterResource(id = cobos.santiago.R.drawable.vinyl_background),
            contentDescription = "Vinyl Background"
        )

        // Vinyl song cover
        MyVinylAnimation()
    }
}

@Composable
fun MyVinylAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(cobos.santiago.R.raw.music_reproduction))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun VinylAnimation(
    isSongPlaying: Boolean = true
) {
    var currentRotation by remember {
        mutableStateOf(0f)
    }

    val rotation = remember {
        Animatable(currentRotation)
    }

    LaunchedEffect(isSongPlaying) {
        if (isSongPlaying) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                rotation.animateTo(
                    targetValue = currentRotation + 50,
                    animationSpec = tween(
                        1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }

    Vinyl(rotationDegrees = rotation.value)
}

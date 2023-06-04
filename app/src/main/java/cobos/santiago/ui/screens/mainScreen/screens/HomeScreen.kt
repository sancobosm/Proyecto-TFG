package cobos.santiago.ui.screens.mainScreen.screens

//noinspection SuspiciousImport
import android.R
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cobos.santiago.data.entities.Song
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.screens.componentes.MainPlayerBar
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import cobos.santiago.ui.viewmodels.UIEvent
import cobos.santiago.ui.viewmodels.UIState
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.*

@Composable
fun HomeScreen(
    vm: SimpleMediaViewModel,
    startService: () -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
            .clip(RoundedCornerShape(30.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        MyMusicAnimation()
    }
    MyHomeScreenBody(vm, startService, navController)
}

@Composable
fun MyMusicAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(cobos.santiago.R.raw.music))
    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = LottieConstants.IterateForever
    )
    LottieAnimation(composition = composition, progress = { progress })
}

@Composable
internal fun MyHomeScreenBody(
    vm: SimpleMediaViewModel,
    startService: () -> Unit,
    navController: NavController
) {
    val state = vm.uiState.collectAsStateWithLifecycle()
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 190.dp, start = 10.dp, end = 10.dp, bottom = 50.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
            //      .background(MaterialTheme.colorScheme.background)
        ) {
            when (state.value) {
                UIState.Initial -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center)
                )

                is UIState.Ready -> {
                    LaunchedEffect(true) { // This is only call first time
                        startService()
                    }

                    Box(Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(0.dp)
                                .background(color = MaterialTheme.colorScheme.onSecondary)

                        ) {
                            items(items = vm.songs, key = {
                                it.id
                            }) { song ->
                                MySongItem(
                                    song, vm, song.id.toInt()
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(110.dp))
                            }
                        }
                        ReadyContent(
                            vm = vm,
                            modifier = Modifier
                                .padding(bottom = 40.dp, start = 5.dp, end = 5.dp)
                                .align(Alignment.BottomCenter),
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}

@Composable
private fun ReadyContent(
    vm: SimpleMediaViewModel,
    modifier: Modifier,
    navController: NavController
) {
    SimpleMediaPlayerUI(
        navController = navController,
        modifier = modifier,
        vm = vm,
        playResourceProvider = {
            if (vm.isPlaying) R.drawable.ic_media_pause
            else R.drawable.ic_media_play
        },
        progressProvider = { Pair(vm.progress, vm.progressString) }
    ) { event -> vm.onUIEvent(event) }
}


@Composable
fun SimpleMediaPlayerUI(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: SimpleMediaViewModel,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEvent) -> Unit,
) {
    val (progress) = progressProvider()
    Card(
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp, end = 0.dp, start = 0.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .height(60.dp)
            .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
            .clickable {
                navController.navigate(AppScreens.SecondaryScreen.route)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Alineación a la derecha

        ) {

            AsyncImage(
                model = vm.songs[vm.currentIndex].imageUrl,
                contentDescription = "Current song image",
                Modifier
                    .size(45.dp)
                    .clip(androidx.compose.material.MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = vm.songs[vm.currentIndex].name + "  -  " + vm.mediaItemList[vm.currentIndex].mediaMetadata.albumTitle,
                //     style = textStyleName,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )
            Image(
                painter = painterResource(id = playResourceProvider()),
                contentDescription = "Play/Pause Button",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = { onUiEvent(UIEvent.PlayPause) })
                    .padding(0.dp)
                    .size(46.dp)
            )
        }
        MainPlayerBar(
            progress = progress,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun MySongItem(
    song: Song,
    vm: SimpleMediaViewModel,
    index: Int
) {
    ConstraintLayout(modifier = Modifier
        .clickable {
            Log.i("informacion", "INDICE: $index")
            vm.playSongAtIndex(index)
            vm.currentIndex = index
        }
        .fillMaxWidth()) {
        val (divider, image, songTitle, songSubtitle, heartIcon, menuIcon) = createRefs()

        Divider(
            Modifier
                .size(1.dp)
                .constrainAs(divider) {
                    top.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)

                    width = Dimension.fillToConstraints
                }, color = MaterialTheme.colorScheme.background
        )

        AsyncImage(
            model = song.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                })

        Text(text = vm.mediaItemList[index].mediaMetadata.displayTitle.toString(),
            maxLines = 2,
            style = androidx.compose.material.MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(songTitle) {
                linkTo(
                    start = image.end,
                    end = parent.end,
                    startMargin = 16.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, 16.dp)
                width = Dimension.preferredWrapContent
            })

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = vm.mediaItemList[index].mediaMetadata.albumTitle.toString(),
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.constrainAs(songSubtitle) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = 16.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(songTitle.bottom, 6.dp)
                    width = Dimension.preferredWrapContent
                })
        }

        IconButton(onClick = { /* Acción al hacer clic en el icono de corazón */ },
            modifier = Modifier.constrainAs(heartIcon) {
                start.linkTo(songSubtitle.start, 150.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(menuIcon.start)
                width = Dimension.wrapContent
            }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite",
                tint = Color.Red
            )
        }

        IconButton(onClick = { /* Acción al hacer clic en el icono de menú */ },
            modifier = Modifier.constrainAs(menuIcon) {
                end.linkTo(parent.end, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.wrapContent
            }) {
            Icon(
                imageVector = Icons.Filled.MoreVert, contentDescription = "Menu", tint = Color.Gray
            )
        }
    }
}

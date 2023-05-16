package cobos.santiago.ui.screens.mainScreen

import android.R
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cobos.santiago.data.entities.Song
import cobos.santiago.ui.screens.componentes.PlayerBar
import cobos.santiago.ui.theme.SpotifyTextStyleDark
import cobos.santiago.ui.theme.SpotifyTextStyleDarkArtist
import cobos.santiago.ui.theme.SpotifyTextStyleLight
import cobos.santiago.ui.theme.SpotifyTextStyleLigtArtist
import cobos.santiago.ui.viewmodels.BottomBarViewModel
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import cobos.santiago.ui.viewmodels.UIEvent
import cobos.santiago.ui.viewmodels.UIState
import com.google.accompanist.coil.rememberCoilPainter


@Composable
internal fun HomeScreen(
    vm: SimpleMediaViewModel,
    startService: () -> Unit
) {
    val state = vm.uiState.collectAsStateWithLifecycle()
    val navigationViewModel = hiltViewModel<BottomBarViewModel>()

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
                        modifier = Modifier.padding(0.dp)
                        /*.weight(6f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)*/
                    ) {
                        items(items = vm.songs, key = {
                            it.id
                        }) { song ->
                            MySongItem(
                                song.name, Modifier.height(70.dp), song, vm, song.id.toInt()
                            )
                        }
                        // Agregar un espacio adicional al final
                        item {
                            Spacer(modifier = Modifier.height(110.dp))
                        }
                    }
                    ReadyContent(
                        vm = vm,
                        modifier = Modifier
                            .padding(bottom = 59.dp, start = 5.dp, end = 5.dp)
                            .align(Alignment.BottomCenter)
                        // .weight(1f)
                    )

                }
            }
        }
    }

}

@Composable
private fun ReadyContent(
    vm: SimpleMediaViewModel, modifier: Modifier
) {
    SimpleMediaPlayerUI(
        durationString = vm.formatDuration(vm.duration),
        vm = vm,
        playResourceProvider = {
            if (vm.isPlaying) R.drawable.ic_media_pause
            else R.drawable.ic_media_play
        },
        progressProvider = { Pair(vm.progress, vm.progressString) },
        onUiEvent = { event -> vm.onUIEvent(event) },
        modifier = modifier
    )
}


@Composable
fun SimpleMediaPlayerUI(
    modifier: Modifier = Modifier,
    vm: SimpleMediaViewModel,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEvent) -> Unit,
) {
    val (progress, progressString) = progressProvider()
    val isDarkTheme = isSystemInDarkTheme()

    val textStyleName = if (isDarkTheme) SpotifyTextStyleDark else SpotifyTextStyleLight
    val textStyleArtist =
        if (isDarkTheme) SpotifyTextStyleDarkArtist else SpotifyTextStyleLigtArtist
    Card(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .height(60.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 5.dp)
        ) {
            Spacer(modifier = Modifier.size(2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Alineación a la derecha

            ) {

                Image(
                    painter = rememberCoilPainter(request = vm.songs[vm.currentIndex].imageUrl),
                    contentDescription = "Current song image",
                    Modifier
                        .size(45.dp)
                        .clip(androidx.compose.material.MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = vm.songs[vm.currentIndex].name + "  ■  " + vm.songs[vm.currentIndex].name,
                    style = textStyleName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                )
                /*Text(
                    text = vm.songs[vm.currentIndex].name,
                    style = textStyleArtist,
                    modifier = Modifier.padding(bottom = 0.dp, start = 0.dp, end = 0.dp)
                )*/

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
            PlayerBar(
                progress = progress,
                durationString = durationString,
                progressString = progressString,
                onUiEvent = onUiEvent
            )
        }
    }
}

@Composable
fun MySongItem(
    name: String, modifier: Modifier, song: Song, vm: SimpleMediaViewModel, index: Int
) {
    ConstraintLayout(modifier = Modifier
        .clickable {
            Log.i("informacion", "INDICE: $index")
            vm.playSongAtIndex(index)
            vm.currentIndex = index
        }
        .fillMaxWidth()) {
        val (divider, image, songTitle, songSubtitle, heartIcon, menuIcon) = createRefs()

        Divider(Modifier.constrainAs(divider) {
            top.linkTo(parent.top)
            centerHorizontallyTo(parent)

            width = Dimension.fillToConstraints
        })

        Image(painter = rememberCoilPainter(song.imageUrl, fadeIn = true),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(androidx.compose.material.MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                })

        Text(text = song.name,
            maxLines = 2,
            style = androidx.compose.material.MaterialTheme.typography.subtitle1,
            color = Color(0xFFB5B5B5),
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
            Text(text = song.name,
                maxLines = 2,
                style = androidx.compose.material.MaterialTheme.typography.subtitle2,
                color = Color(0xFFB5B5B5),
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


/*

@Composable
fun HomeMusicScreen(
    vm: SimpleMediaViewModel = hiltViewModel(), startService: () -> Unit
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            modifier = Modifier.fillMaxSize(), vm = vm, startService
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier, vm: SimpleMediaViewModel, startService: () -> Unit
) {
    val state = vm.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        val appBarColor = Color.LightGray
        Spacer(
            Modifier
                //    .background(appBarColor)
                .fillMaxWidth()
        )
        when (state.value) {
            UIState.Initial -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    androidx.compose.material.CircularProgressIndicator(
                        //     color = androidx.compose.material.MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxHeight()

                            .align(Alignment.Center)
                            .padding(
                                top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp
                            )
                    )
                }

            }
            is UIState.Ready -> {
                LaunchedEffect(true) { // This is only call first time
                    startService()
                }
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.background(androidx.compose.material.MaterialTheme.colors.background)
                            .padding(bottom = 60.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        items(
                            items = vm.songs,
                        ) {
                            MusicListItem(
                                vm = vm, song = it, index = it.id.toInt()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MusicListItem(
    vm: SimpleMediaViewModel, song: Song, index: Int
) {
    ConstraintLayout(modifier = Modifier
        .clickable {
            println("${index}*******************************index*************")
            vm.playSongAtIndex(index)
        }
        .fillMaxWidth()) {
        val (divider, songTitle, songSubtitle, image, playIcon) = createRefs()

        Divider(Modifier.constrainAs(divider) {
            top.linkTo(parent.top)
            centerHorizontallyTo(parent)

            width = Dimension.fillToConstraints
        })

        Image(painter = rememberCoilPainter(song.imageUrl, fadeIn = true),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                })

        Text(text = song.name,
            maxLines = 2,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.constrainAs(songTitle) {
                linkTo(
                    start = parent.start,
                    end = image.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
                width = Dimension.preferredWrapContent
            })

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = song.name,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.constrainAs(songSubtitle) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        startMargin = 24.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(songTitle.bottom, 6.dp)
                    start.linkTo(parent.start, 16.dp)
                    width = Dimension.preferredWrapContent
                })
        }
    }
}

*/

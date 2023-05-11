package cobos.santiago.ui.componentes.musicComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cobos.santiago.data.entities.Song
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import cobos.santiago.ui.viewmodels.UIState
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun HomeMusicScreen(
    vm: SimpleMediaViewModel = hiltViewModel(),
    startService: () -> Unit
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            modifier = Modifier.fillMaxSize(),
            vm = vm,
            startService
        )
    }
}


@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    vm: SimpleMediaViewModel,
    startService: () -> Unit
) {
    val state = vm.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)
        Spacer(
            Modifier
                .background(appBarColor)
                .fillMaxWidth()
        )
        when (state.value) {
            UIState.Initial -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxHeight()

                            .align(Alignment.Center)
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                    )
                }

            }
            is UIState.Ready -> {
                LaunchedEffect(true) { // This is only call first time
                    startService()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                )
                {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.background)
                            .padding(bottom = 60.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        items(
                            items = vm.songs,
                        ) {
                            MusicListItem(
                                vm = vm,
                                song = it,
                                index = it.id.toInt()
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
    vm: SimpleMediaViewModel,
    song: Song,
    index: Int
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable {
                println("${index}*******************************index*************")
                vm.playSongAtIndex(index)
            }
            .fillMaxWidth()
    ) {
        val (
            divider, songTitle, songSubtitle, image, playIcon
        ) = createRefs()

        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)

                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = rememberCoilPainter(song.imageUrl, fadeIn = true),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
        )

        Text(
            text = song.name,
            maxLines = 2,
            style = MaterialTheme.typography.subtitle1,
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
            }
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = song.name,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle2,
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
                }
            )
        }
    }
}
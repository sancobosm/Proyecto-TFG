package cobos.santiago.ui.mainScreen

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cobos.santiago.ui.componentes.PlayerBar
import cobos.santiago.ui.componentes.PlayerControls
import cobos.santiago.ui.viewmodels.SimpleMediaViewModel
import cobos.santiago.ui.viewmodels.UIEvent
import cobos.santiago.ui.viewmodels.UIState


@Composable
internal fun HomeScreen(
    vm: SimpleMediaViewModel,
    startService: () -> Unit,
) {
    val state = vm.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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

                Column {
                    LazyColumn(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(
                            items = vm.songs,
                            key = {
                                it.id
                            }
                        ) { song ->
                            MySongItem(song.name, Modifier.height(70.dp))
                        }
                    }

                    ReadyContent(vm = vm)
                }
            }

        }
    }
}

@Composable
private fun ReadyContent(
    vm: SimpleMediaViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleMediaPlayerUI(
            durationString = vm.formatDuration(vm.duration),
            playResourceProvider = {
                if (vm.isPlaying) R.drawable.ic_media_pause
                else R.drawable.ic_media_play
            },
            progressProvider = { Pair(vm.progress, vm.progressString) },
            onUiEvent = { event -> vm.onUIEvent(event) },
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun SimpleMediaPlayerUI(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEvent) -> Unit
) {
    val (progress, progressString) = progressProvider()

    Box(
        modifier = modifier
            .padding(16.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
}

@Composable
fun MySongItem(name: String, modifier: Modifier) {
    Card(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {

        }
    }
}


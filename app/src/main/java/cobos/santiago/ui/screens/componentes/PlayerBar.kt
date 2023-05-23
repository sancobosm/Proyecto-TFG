package cobos.santiago.ui.screens.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cobos.santiago.ui.viewmodels.UIEvent

/*
@Composable
internal fun PlayerBar(
    progress: Float,
    durationString: String,
    progressString: String,
    onUiEvent: (UIEvent) -> Unit
) {
    val newProgressValue = remember { mutableStateOf(0f) }
    val useNewProgressValue = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Slider(
            value = if (useNewProgressValue.value) newProgressValue.value else progress,
            onValueChange = { newValue ->
                useNewProgressValue.value = true
                newProgressValue.value = newValue
                onUiEvent(UIEvent.UpdateProgress(newProgress = newValue))
            },
            onValueChangeFinished = {
                useNewProgressValue.value = false
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = progressString)
            Text(text = durationString)
        }
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerBar(
    progress: Float,
    durationString: String,
    progressString: String,
    onUiEvent: (UIEvent) -> Unit
) {
    val newProgressValue = remember { mutableStateOf(0f) }
    val useNewProgressValue = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Agregar esta línea
    ) {

    }
    Slider(
        thumb = {},
        value = if (useNewProgressValue.value) newProgressValue.value else progress,
        onValueChange = { newValue ->
            useNewProgressValue.value = true
            newProgressValue.value = newValue
            onUiEvent(UIEvent.UpdateProgress(newProgress = newValue))
        },
        onValueChangeFinished = {
            useNewProgressValue.value = false
        },
        modifier = Modifier
            .padding(horizontal = 0.dp)
    )
}
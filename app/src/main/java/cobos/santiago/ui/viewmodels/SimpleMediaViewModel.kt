package cobos.santiago.ui.viewmodels

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import cobos.santiago.data.entities.Song
import com.cursokotlin.music_service.service.PlayerEvent
import com.cursokotlin.music_service.service.SimpleMediaServiceHandler
import com.cursokotlin.music_service.service.SimpleMediaState
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SimpleMediaViewModel @Inject constructor(
    private val simpleMediaServiceHandler: SimpleMediaServiceHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    val db = Firebase.firestore
    private val docRef = db.collection("songs")

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _index = MutableStateFlow(0)
    val index = _index.asStateFlow()


    val songs = mutableListOf<Song>()
    val mediaItemList = mutableListOf<MediaItem>()

    var currentIndex by savedStateHandle.saveable { mutableStateOf(0) }

    init {
        viewModelScope.launch {
            getAllSongs()
            simpleMediaServiceHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
                    is SimpleMediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    SimpleMediaState.Initial -> _uiState.value = UIState.Initial
                    is SimpleMediaState.Playing -> isPlaying = mediaState.isPlaying
                    is SimpleMediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is SimpleMediaState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }
                }
            }
        }
    }

    fun playSongAtIndex(index: Int) {
        _index.value = index
        Log.i("informacion", "${_index.value} _index.value")
        viewModelScope.launch {
            simpleMediaServiceHandler.playSongAtIndex(index)

        }
    }

    fun getAllSongs() {
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val documentSnapshotList = document.documents
                    println("${loadData(documentSnapshotList)}************************************************************")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }

    fun getCurrentSong() {
        val song = simpleMediaServiceHandler.currentSong()
        // currentSong = findSongById(song?.id)
    }

    private fun findSongById(id: Int?): Song? {
        return songs.find { it.id.toInt() == id }
    }

    override fun onCleared() {
        viewModelScope.launch {
            simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
        }
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.Backward -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Backward)
            UIEvent.Forward -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Forward)
            UIEvent.Next -> {
                _index.value = simpleMediaServiceHandler.currentIndex.value
                simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Next)
            }
            UIEvent.PlayPause -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
            is UIEvent.UpdateProgress -> {
                progress = uiEvent.newProgress
                simpleMediaServiceHandler.onPlayerEvent(
                    PlayerEvent.UpdateProgress(
                        uiEvent.newProgress
                    )
                )
            }
        }
    }

    fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        progressString = formatDuration(currentProgress)
    }


    private fun loadData(documentSnapshotList: List<DocumentSnapshot>): Int {
        //val mediaItemList = mutableListOf<MediaItem>()
        for (documentSnapshot in documentSnapshotList) {
            val id = documentSnapshot.id
            val name = documentSnapshot.getString("name")
            val imageUrl = documentSnapshot.getString("imageUrl")
            val songUrl = documentSnapshot.getString("songUrl")
            val song = Song()

            if (songUrl != null && imageUrl != null && name != null) {
                song.id = id
                song.name = name
                song.imageUrl = imageUrl
                song.songUrl = songUrl

                songs.add(song)

                val mediaItem = MediaItem.Builder()
                    .setMediaId(id)
                    .setUri(songUrl)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                            .setArtworkUri(Uri.parse(imageUrl))
                            .setAlbumTitle("SoundHelix")
                            .setDisplayTitle(name)
                            .build()
                    )
                    .build()
                mediaItemList.add(mediaItem)
                simpleMediaServiceHandler.mediaItemList.add(mediaItem)
            }
        }


        simpleMediaServiceHandler.addMediaItemList(mediaItemList)

        return mediaItemList.size
    }
}

sealed class UIEvent {
    object PlayPause : UIEvent()
    object Backward : UIEvent()
    object Forward : UIEvent()
    object Next : UIEvent()
    data class UpdateProgress(val newProgress: Float) : UIEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}


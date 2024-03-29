package com.cursokotlin.music_service.service

import android.annotation.SuppressLint
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SimpleMediaServiceHandler @Inject constructor(
    private val player: ExoPlayer
) : Player.Listener {

    private val _simpleMediaState = MutableStateFlow<SimpleMediaState>(SimpleMediaState.Initial)
    val simpleMediaState = _simpleMediaState.asStateFlow()

    private var job: Job? = null

    val mediaItemList = mutableListOf<MediaItem>()

    val currentIndex = MutableStateFlow(0)
    val mediaItemCount = MutableStateFlow(0)

    init {
        player.addListener(this)
        job = Job()
    }

    fun addMediaItem(mediaItem: MediaItem) {
        player.setMediaItem(mediaItem)
        player.prepare()
    }


    suspend fun playSongAtIndex(index: Int) {
        currentIndex.value = index
        if (index >= 0) {
            //    player.setMediaItem(mediaItemList[index])
            player.setMediaItem(getMediaItemAtIndex(index))
            player.prepare()
            player.play()
            _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = true)
            startProgressUpdate()
        } else {
            player.stop()
        }
    }

    fun getMediaItemAtIndex(index: Int): MediaItem {
        addMediaItemList(mediaItemList)
        mediaItemCount.value = player.mediaItemCount
        val mediaItem: MediaItem = player.getMediaItemAt(index)
        val mediaItemCount = player.mediaItemCount
        Log.i("informacion", "${mediaItemCount} COUNT")
        return mediaItem
    }

    fun addMediaItemList(mediaItemListAdd: List<MediaItem>) {
        //println("${mediaItemList.get(3).mediaId}****************mediaId")
        Log.i("informacion", "${mediaItemList.size} MEDIA ITEM LIST")
        player.setMediaItems(mediaItemListAdd)
        player.prepare()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    suspend fun onPlayerEvent(playerEvent: PlayerEvent) {
        when (playerEvent) {
            PlayerEvent.Backward -> player.seekBack()
            PlayerEvent.Forward -> player.seekForward()
            PlayerEvent.Next -> {
                //  Log.i("informacion", "${currentIndex.value} player.currentWindowIndex")
                val nextIndex = currentIndex.value + 1
                // val mediaItemCount = player.mediaItemCount
                //   Log.i("informacion", "${mediaItemCount} player.mediaItemCount")
                if (nextIndex < mediaItemCount.value) {
                    playSongAtIndex(nextIndex)
                    //      Log.i("informacion", "SE METE")
                } else {
                    //      Log.i("informacion", "NO SE METE")
                    // Si no hay más canciones en la cola, detener la reproducción
                    player.stop()
                    _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = false)
                }
            }
            PlayerEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                    stopProgressUpdate()
                } else {
                    player.play()
                    _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = true)
                    startProgressUpdate()
                }
            }
            PlayerEvent.Stop -> stopProgressUpdate()
            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())
        }
    }

    fun currentSong(): MediaItem? {
        return player.currentMediaItem
    }


    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _simpleMediaState.value =
                SimpleMediaState.Buffering(player.currentPosition)
            ExoPlayer.STATE_READY -> _simpleMediaState.value =
                SimpleMediaState.Ready(player.duration)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = isPlaying)
        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        } else {
            stopProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _simpleMediaState.value = SimpleMediaState.Progress(player.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = false)
    }
}

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object Stop : PlayerEvent()
    object Next : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}

sealed class SimpleMediaState {
    object Initial : SimpleMediaState()
    data class Ready(val duration: Long) : SimpleMediaState()
    data class Progress(val progress: Long) : SimpleMediaState()
    data class Buffering(val progress: Long) : SimpleMediaState()
    data class Playing(val isPlaying: Boolean) : SimpleMediaState()
}
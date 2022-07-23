package com.ziyad.core.utils.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.ziyad.core.domain.model.Radio

class RadioPlayer(radio: Radio, context: Context) {
    var getRadio = radio
    var player = ExoPlayer.Builder(context)
        .setMediaSourceFactory(
            DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(5000)
        )
        .build()
    private var mediaItem: MediaItem = MediaItem.Builder()
        .setUri(Uri.parse(getRadio.url_resolved))
        .setLiveConfiguration(
            MediaItem.LiveConfiguration.Builder()
                .setMaxPlaybackSpeed(1.02f)
                .build()
        )
        .build()

    init {
        player.setMediaItem(mediaItem)
        player.prepare()
        play()
    }

    fun setRadio(radio: Radio) {
        this.getRadio = radio
        val newUrl = radio.url_resolved
        val mediaItem: MediaItem = MediaItem.Builder()
            .setUri(Uri.parse(newUrl))
            .setLiveConfiguration(
                MediaItem.LiveConfiguration.Builder()
                    .setMaxPlaybackSpeed(1.02f)
                    .build()
            )
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    private fun play() {
        player.play()
    }

    fun getName() = this.getRadio.name

    companion object {
        @Volatile
        private var INSTANCE: RadioPlayer? = null

        @JvmStatic
        fun getPlayer(radio: Radio, context: Context): RadioPlayer {
            if (INSTANCE == null) {
                synchronized(RadioPlayer::class.java) {
                    INSTANCE = RadioPlayer(radio, context)
                }
            }
            return INSTANCE as RadioPlayer
        }

        @JvmStatic
        fun getPlayer(): RadioPlayer? {
            return INSTANCE
        }
    }
}
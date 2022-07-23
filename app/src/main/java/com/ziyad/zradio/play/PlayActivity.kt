package com.ziyad.zradio.play

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.ziyad.zradio.R
import com.ziyad.zradio.databinding.ActivityPlayBinding
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.utils.player.RadioPlayer
import com.ziyad.core.utils.Utils.loadImage


class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private val model: PlayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val radio = intent.getParcelableExtra<Radio>(SELECTED_RADIO)!!
        model.setRadio(radio)
        model.radio.observe(this) {
            binding.apply {
                tvNamePlay.text = it.name
                tvStatePlay.text = it.state
                ivFavicon.loadImage(it.favicon)
                playerControl.player =
                    RadioPlayer.getPlayer(it, this@PlayActivity).player
                RadioPlayer.getPlayer()?.setRadio(it)
            }
            createNotificationChannel()
            createRadioNotification(it, this)
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createRadioNotification(radio: Radio, context: Context) {
        val resultIntent = Intent(context, PlayActivity::class.java).run {
            putExtra(SELECTED_RADIO, radio)
        }
        val resultPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_baseline_radio_24)
            .setContentIntent(resultPendingIntent)
            .setContentTitle(radio.name)
            .setContentText(radio.state)
            .build()
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        const val SELECTED_RADIO = "selected_radio"
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
        const val CHANNEL_DESCRIPTION = "channel_description"
        const val NOTIFICATION_ID = 1
    }
}
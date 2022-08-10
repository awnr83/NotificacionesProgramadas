package com.moappdev.notificacionesprogramadas.service


import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.moappdev.notificacionesprogramadas.R
import com.moappdev.notificacionesprogramadas.RingActivity
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.CHANNEL_ID
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.TITLE


class AlarmService: Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrate: Vibrator

    override fun onCreate() {
        super.onCreate()

        mediaPlayer= MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.isLooping=true
        mediaPlayer.start()
//        vibrate= getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, RingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val alarmTitle= String.format("Recordatorio: %s",intent.getStringExtra(TITLE))

        val notification= NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(alarmTitle)
            .setContentText("Recuerde tomar la medicación: $alarmTitle")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()

        val pattern = longArrayOf(0, 100, 1000)
  //      vibrate.vibrate(pattern, 0)
        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    //    vibrate.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
package com.example.internetspeedmonitor.model.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.internetspeedmonitor.R
import com.example.internetspeedmonitor.model.repository.InternetSpeedRepository
import com.example.internetspeedmonitor.model.room.InternetRoomDatabase
import com.example.internetspeedmonitor.model.room.InternetSpeed
import com.example.internetspeedmonitor.model.utils.Utils
import com.example.internetspeedmonitor.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InternetSpeedMonitorService : Service() {
    private lateinit var notification: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManagerCompat
    private val channelId = "INTERNET_S"
    private val notificationId = 2
    private var isStarted = false
    private lateinit var repository: InternetSpeedRepository
    private val internetSpeedBinder = InternetSpeedBinder()
    private var internetSpeed = 0L

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_network)
            .setContentTitle(getString(R.string.current_ntw_speed))
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(getPendingIntent())

        startForeground(notificationId, notification.build())

        val database = InternetRoomDatabase.getRoomDatabase(applicationContext)
        repository = InternetSpeedRepository(database.getInternetSpeedDAO())
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            channelId,
            getString(R.string.network_speed),
            NotificationManager.IMPORTANCE_MIN
        )
        notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(applicationContext, MainActivity::class.java)
        return PendingIntent.getActivity(
            applicationContext,
            2,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isStarted) {
            CoroutineScope(Dispatchers.Default).launch {
                while (true) {
                    internetSpeed = InternetSpeedHandler().getInternetSpeed()
                    if (internetSpeed != 0L) {
                        repository.saveInternetSpeed(InternetSpeed(speed = internetSpeed))
                    }
                    notification.setContentText(Utils.formatSpeed(internetSpeed))
                    notificationManager.notify(notificationId, notification.build())
                    delay(9000)
                }
            }
            isStarted = true
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return internetSpeedBinder
    }

    fun getInternetSpeed(): Long {
        return internetSpeed
    }

    inner class InternetSpeedBinder : Binder() {
        fun getService(): InternetSpeedMonitorService {
            return this@InternetSpeedMonitorService
        }
    }
}
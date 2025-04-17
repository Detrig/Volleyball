package com.example.disputer.notification.domain.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.disputer.R
import com.example.disputer.notification.data.NotificationData

class NotificationHelper(
    private val context: Context
) {

    fun showNotification(data: NotificationData) {
        val channelId = "notif_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создание канала (только для Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Уведомления",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(data.title)
            .setContentText(data.body)
            .setSmallIcon(R.drawable.volleylogo)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(data.id.hashCode(), notification)
    }
}
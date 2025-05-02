package com.example.disputer.notification.presentation

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.disputer.notification.data.FirebaseNotificationRepository
import com.example.disputer.notification.domain.utils.NotificationHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return Result.success()

        // Получаем репозиторий вручную
        val database = FirebaseDatabase.getInstance()
        val repository = FirebaseNotificationRepository(database)

        val notifications = repository.getNotificationsForUser(userId)
        if (notifications.isNotEmpty()) {
            val latest = notifications.maxByOrNull { it.timestamp }
            latest?.let {
                NotificationHelper(context).showNotification(it)
            }
        }

        return Result.success()
    }

//    override suspend fun doWork(): Result {
//        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return Result.failure()
//        val firebaseDatabase = FirebaseDatabase.getInstance()
//        val notificationHelper = NotificationHelper(applicationContext)
//
//        val sharedPref = applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
//        val lastCheck = sharedPref.getLong("last_check_time", 0L)
//        val now = System.currentTimeMillis()
//
//        val snapshot = firebaseDatabase
//            .getReference("notifications/$userId")
//            .get()
//            .await()
//
//        val newNotifications = snapshot.children.mapNotNull { child ->
//            val id = child.key ?: return@mapNotNull null
//            val title = child.child("title").getValue(String::class.java)
//            val body = child.child("body").getValue(String::class.java)
//            val timestamp = child.child("timestamp").getValue(Long::class.java)
//
//            if (title != null && body != null && timestamp != null && timestamp > lastCheck) {
//                NotificationData(id, title, body, timestamp)
//            } else null
//        }
//
//        newNotifications.forEach {
//            notificationHelper.showNotification(it)
//        }
//
//        // Сохраняем новое время последней проверки
//        sharedPref.edit { putLong("last_check_time", now) }
//
//        return Result.success()
//    }
}
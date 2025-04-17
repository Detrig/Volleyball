package com.example.disputer.notification.data

import android.util.Log
import com.example.disputer.notification.domain.repo.NotificationRepository
import com.example.disputer.notification.domain.utils.NotificationHelper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseNotificationRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : NotificationRepository {

    override suspend fun getNotificationsForUser(userId: String): List<NotificationData> {
        val snapshot = firebaseDatabase
            .getReference("notifications/$userId")
            .orderByKey()
            .limitToLast(1)
            .get()
            .await()

        return snapshot.children.mapNotNull { child ->
            val id = child.key ?: return@mapNotNull null
            val title = child.child("title").getValue(String::class.java)
            val body = child.child("body").getValue(String::class.java)
            val timestamp = child.child("timestamp").getValue(Long::class.java)

            if (title != null && body != null && timestamp != null) {
                NotificationData(id, title, body, timestamp)
            } else null
        }
    }

    override suspend fun sendNotification(trainingId: String, trainingDate: String, userIds: List<String>) {
        val notificationData = NotificationData(
            id = trainingId,
            title = "Тренировка отменена",
            body = "Тренировка $trainingDate была отменена.",
            timestamp = System.currentTimeMillis()
        )

        userIds.forEach { userId ->
            Log.d("VB-21", "sendNotification $trainingDate to user: $userId")
            firebaseDatabase.getReference("notifications/$userId").push().setValue(notificationData)
        }
    }
}
package com.example.disputer.notification.domain.repo

import com.example.disputer.notification.data.NotificationData

interface NotificationRepository {
    suspend fun getNotificationsForUser(userId: String): List<NotificationData>
    suspend fun sendNotification(trainingId: String, trainingDate: String, userIds: List<String>)
}
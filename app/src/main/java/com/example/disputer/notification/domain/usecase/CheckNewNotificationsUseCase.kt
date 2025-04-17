package com.example.disputer.notification.domain.usecase

import com.example.disputer.notification.data.NotificationData
import com.example.disputer.notification.domain.repo.NotificationRepository
import javax.inject.Inject

class CheckNewNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(userId: String): List<NotificationData> {
        return repository.getNotificationsForUser(userId)
            .filter { it.timestamp > getLastCheckedTimestamp() }
    }

    private fun getLastCheckedTimestamp(): Long {
        // Здесь можно получить из SharedPreferences
        return 0L
    }
}
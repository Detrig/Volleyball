package com.example.disputer.notification.domain.utils

import android.content.Context
import com.example.disputer.notification.data.NotificationData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

object NotificationObserver {

    fun observeNotifications(context: Context, userId: String) {
        val ref = FirebaseDatabase.getInstance().getReference("notifications/$userId")
        val notificationHelper = NotificationHelper(context)

        // Зафиксируем время запуска наблюдения
        val lastObservedTime = System.currentTimeMillis()

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val title = snapshot.child("title").getValue(String::class.java)
                val body = snapshot.child("body").getValue(String::class.java)
                val timestamp = snapshot.child("timestamp").getValue(Long::class.java)
                val id = snapshot.key ?: return

                // Показываем только новые уведомления
                if (title != null && body != null && timestamp != null && timestamp > lastObservedTime) {
                    val notification = NotificationData(id, title, body, timestamp)
                    notificationHelper.showNotification(notification)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
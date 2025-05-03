package com.example.disputer.notification.domain.utils

import com.example.disputer.core.ListLiveDataWrapper
import com.example.disputer.notification.data.NotificationData

interface NotificationsListLiveDataWrapper : ListLiveDataWrapper.All<NotificationData> {
    class Base : NotificationsListLiveDataWrapper, ListLiveDataWrapper.Abstract<NotificationData>()
}
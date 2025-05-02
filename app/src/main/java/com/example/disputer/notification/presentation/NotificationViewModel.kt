package com.example.disputer.notification.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.notification.domain.usecase.CheckNewNotificationsUseCase
import com.example.disputer.notification.domain.utils.NotificationsListLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationViewModel(
    private val checkNewNotificationsUseCase: CheckNewNotificationsUseCase,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val notificationsListLiveDataWrapper: NotificationsListLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun notificationsLiveData() = notificationsListLiveDataWrapper.liveData()

    fun getAllNotificationsForCurrentUser() {
        val currentUser = currentUserLiveDataWrapper.liveData().value

        viewModelScope.launch(dispatcherIo) {
            if (currentUser is AuthUser.ParentUser) {
                val notifications = checkNewNotificationsUseCase(currentUser.parent.uid)
                withContext(dispatcherMain) {
                    notificationsListLiveDataWrapper.update(notifications)
                }
            }
            else if (currentUser is AuthUser.CoachUser) {
                val notifications = checkNewNotificationsUseCase(currentUser.coach.uid)
                withContext(dispatcherMain) {
                    notificationsListLiveDataWrapper.update(notifications)
                }
            }
        }
    }
}